package com.example.tetianapriadko.people;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.example.tetianapriadko.people.dialog_fragments.DlgFragExit;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String NAME = "name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        addFragmentBackStack(new FragListAll());

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BackendlessUser user = Backendless.UserService.CurrentUser();

        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.email)).setText(user.getEmail());
        ((TextView) navigationView.getHeaderView(0)
                .findViewById(R.id.name)).setText((String) user.getProperty(NAME));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                DlgFragExit dlgFragExit = new DlgFragExit();
                dlgFragExit.setTargetActivity(this, 2);
                dlgFragExit.show(getSupportFragmentManager(), dlgFragExit.getDialogTag());
            } else {
                super.onBackPressed();
            }
        }
    }

    public void customActivityResult(int requestCode, int resultCode) {
        switch (requestCode) {
            case 2:
                switch (resultCode) {
                    case RESULT_OK:
                        finish();
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    protected void replaceFragmentBackStack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frag_container, fragment)
                .addToBackStack("frag")
                .commit();
    }

    protected void addFragmentBackStack(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
//              .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .add(R.id.frag_container, fragment)
                .addToBackStack("frag")
                .commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_student:
                replaceFragmentBackStack(new FragListStudent());
                break;
            case R.id.nav_teacher:
                replaceFragmentBackStack(new FragListTeacher());
                break;
            case R.id.nav_all:
                replaceFragmentBackStack(new FragListAll());
                break;
            case R.id.nav_map:
                startActivity(new Intent(this, MapsActivity.class));
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

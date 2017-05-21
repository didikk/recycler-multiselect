package me.didik.recyclermultiselection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private PersonAdapter adapter;
    private ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        adapter = new PersonAdapter(getPersonList(), listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private List<Person> getPersonList() {
        List<Person> list = new ArrayList<>();
        list.add(new Person("Didik", "Android Developer"));
        list.add(new Person("Ari", "Web Developer"));
        list.add(new Person("Bayu", "Web Developer"));
        list.add(new Person("Afina", "iOs Developer"));
        list.add(new Person("Claudia", "Mahasiswi"));
        list.add(new Person("Iqbal", "Gamer"));
        list.add(new Person("Cynthia", "Enterpreneur"));
        list.add(new Person("Trista", "UI/UX Designer"));

        return list;
    }

    private PersonAdapter.ClickListener listener = new PersonAdapter.ClickListener() {
        @Override
        public void onClick(int position) {
            Toast.makeText(MainActivity.this, "Position: " + position, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTap(int totalSelected) {
            if (totalSelected > 0) mActionMode.setTitle(totalSelected + " selected");
            else mActionMode.finish();
        }

        @Override
        public void onLongClick() {
            mActionMode = startActionMode(mActionModeCallback);
        }
    };

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_select, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_delete) {
                Toast.makeText(MainActivity.this, "Deleted some items.", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode.setTitle("");
            adapter.exitMultiselectMode();
        }
    };
}

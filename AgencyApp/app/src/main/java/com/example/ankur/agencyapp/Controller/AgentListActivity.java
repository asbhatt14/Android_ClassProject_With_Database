package com.example.ankur.agencyapp.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ankur.agencyapp.DAO.AgentDAO;
import com.example.ankur.agencyapp.DAO.MissionDAO;
import com.example.ankur.agencyapp.Model.Agents;
import com.example.ankur.agencyapp.R;

import java.util.ArrayList;
import java.util.List;

public class AgentListActivity extends AppCompatActivity implements AdapterView.OnItemClickListener,View.OnClickListener {

    Toolbar toolbar;
    ListView agentList_lstAgent;
    ArrayList<Agents> listOfAgent;
    AgentListAdapter adapter;
    TextView txtToolbar;
    ImageView imgToolbarBack;
    EditText edtSearch;
    ImageView imgSearch,imgCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);
        initToolBar();
        agentList_lstAgent = (ListView) findViewById(R.id.agentList_lstAgent);
        edtSearch = (EditText) findViewById(R.id.edtSearch);
        imgSearch = (ImageView)  findViewById(R.id.imgSearch);
        imgCancel = (ImageView)  findViewById(R.id.imgCancel);
      //  listOfAgent = new ArrayList<Agents>();

        imgSearch.setOnClickListener(this);
        imgCancel.setOnClickListener(this);

        //String agentId, String agentName, String agencyName, int agentLevel, String agentCountry, String ageentPhoneNumber, String agentURL, String ageentAddress
/*        listOfAgent.add(new Agents(1, "Michel", "ACI","001", "USA","6472447260","www.yahoo.com","225 Van Horne Avenue"));
        listOfAgent.add(new Agents(2, "John", "ACI","005", "USA","6472447260","www.yahoo.com","281 Van Horne Avenue"));
        listOfAgent.add(new Agents(3, "Strack", "ACI","007", "USA","6472447260","www.yahoo.com","231 Van Horne Avenue"));
        listOfAgent.add(new Agents(4, "Cersi", "ACI","003", "USA","6472447260","www.yahoo.com","260 Van Horne Avenue"));
        listOfAgent.add(new Agents(5, "Ankur", "ACI","002", "USA","6472447260","www.yahoo.com","260 Van Horne Avenue"));
        listOfAgent.add(new Agents(6, "Sanjay", "ACI","004", "USA","6472447260","www.yahoo.com","260 Van Horne Avenue"));
        listOfAgent.add(new Agents(7, "Dutt", "ACI","006","USA","6472447260","www.google.com","260 Van Horne Avenue"));*/

        loadAgentList();

        agentList_lstAgent.setOnItemClickListener(this);

        registerForContextMenu(agentList_lstAgent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAgentList();
    }

    private void loadAgentList() {
        AgentDAO agentDAO = new AgentDAO(this);
        listOfAgent = (ArrayList<Agents>) agentDAO.dbSearch();
        agentDAO.close();
        adapter = new AgentListAdapter(getApplicationContext(),listOfAgent);
        agentList_lstAgent.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem delete = menu.add("Delete");

        delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Agents agent = (Agents) agentList_lstAgent.getItemAtPosition(info.position);

                AgentDAO dao = new AgentDAO(AgentListActivity.this);
                dao.dbDelete(agent);
                dao.close();
                Toast.makeText(AgentListActivity.this,"Agent Deleted",Toast.LENGTH_SHORT).show();
                loadAgentList();
                return false;
            }
        });

        MenuItem update = menu.add("Update");
        update.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Agents agent = (Agents) agentList_lstAgent.getItemAtPosition(info.position);

                Intent updateAgent = new Intent(AgentListActivity.this,AddAgentActivity.class);
                updateAgent.putExtra("agent",agent);
                startActivity(updateAgent);
              /*  AgentDAO dao = new AgentDAO(AgentListActivity.this);
                dao.dbUpdate(agent);
                dao.close();
                Toast.makeText(AgentListActivity.this,"Agent Updated",Toast.LENGTH_SHORT).show();
                loadAgentList();*/
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtToolbar = (TextView) toolbar.findViewById(R.id.txtToolbar);
        imgToolbarBack = (ImageView) toolbar.findViewById(R.id.imgToolbarBack);
        //toolbar.setTitle("Agent List");
        txtToolbar.setText("Agent List");

        setSupportActionBar(toolbar);

        imgToolbarBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Agents objAgent = listOfAgent.get(position);
        Intent agentProfile = new Intent(this,AgentProfileActivity.class);
        agentProfile.putExtra("agent", objAgent);
        startActivity(agentProfile);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgSearch:
                searchList();
                break;
            case R.id.imgCancel:
                edtSearch.setText("");
                edtSearch.clearFocus();
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                loadAgentList();
                break;
            default:
                break;
        }
    }

    private void searchList() {
        AgentDAO agentDAO = new AgentDAO(this);

        listOfAgent = (ArrayList<Agents>) agentDAO.dbSearchByName(edtSearch.getText().toString());
        agentDAO.close();
        adapter = new AgentListAdapter(getApplicationContext(),listOfAgent);
        agentList_lstAgent.setAdapter(adapter);
    }
}

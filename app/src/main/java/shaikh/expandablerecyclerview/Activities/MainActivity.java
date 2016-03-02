package shaikh.expandablerecyclerview.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import shaikh.expandablerecyclerview.Adapter.ExpandableAdapter;
import shaikh.expandablerecyclerview.Model.DataModel;
import shaikh.expandablerecyclerview.Model.FoodModel;
import shaikh.expandablerecyclerview.Model.ItemModel;
import shaikh.expandablerecyclerview.R;

public class MainActivity extends AppCompatActivity implements ExpandableAdapter.OnChildClickListener {
    private ExpandableAdapter mAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        initialize();
    }

    private void setToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.expandable_recycler_view);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initialize() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mAdapter = new ExpandableAdapter(this, makeDataItem(getData()));
        mAdapter.setOnChildClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private ArrayList<FoodModel> getData() {
        ArrayList<FoodModel> foodModels = new ArrayList<>();

        ItemModel i1 = new ItemModel("Chicken 69");
        ItemModel i2 = new ItemModel("Chicken Biryani");

        ArrayList<ItemModel> list1 = new ArrayList<>();

        list1.add(i1);
        list1.add(i2);

        foodModels.add(new FoodModel("Indian", list1));

        ItemModel i3 = new ItemModel("Pizza");
        ItemModel i4 = new ItemModel("Pasta");
        ItemModel i5 = new ItemModel("Risotto");
        ArrayList<ItemModel> list2 = new ArrayList<>();
        list2.add(i3);
        list2.add(i4);
        list2.add(i5);


        foodModels.add(new FoodModel("Italian", list2));

        ItemModel i6 = new ItemModel("French Crepes");
        ItemModel i7 = new ItemModel("Brandied Cherry Clafouti");
        ItemModel i8 = new ItemModel("Salade Nicoise");
        ItemModel i9 = new ItemModel("French Bread");
        ItemModel i10 = new ItemModel("French Soup");
        ArrayList<ItemModel> list3 = new ArrayList<>();
        list3.add(i6);
        list3.add(i7);
        list3.add(i8);
        list3.add(i9);
        list3.add(i10);

        foodModels.add(new FoodModel("French", list3));
        foodModels.add(new FoodModel("Continental", null));

        return foodModels;
    }//end of getData

    ArrayList<DataModel> data;

    private ArrayList<DataModel> makeDataItem(ArrayList<FoodModel> foodModels) {
        data = new ArrayList<>();
        if (foodModels != null && !foodModels.isEmpty()) {
            for (int i = 0; i < foodModels.size(); i++) {
                data.add(new DataModel(foodModels.get(i).getName(), 0));
                if (foodModels.get(i).getItemModels() != null && !foodModels.get(i).getItemModels().isEmpty()) {
                    for (int j = 0; j < foodModels.get(i).getItemModels().size(); j++) {
                        data.add(new DataModel(foodModels.get(i).getItemModels().get(j).getItemName(), 1));
                    }
                }
            }
        }
        return data;
    }

    @Override
    public void onChildClick(int position) {
        Toast.makeText(this, getString(R.string.clicked) + data.get(position).getItemName(), Toast.LENGTH_LONG).show();
    }
}

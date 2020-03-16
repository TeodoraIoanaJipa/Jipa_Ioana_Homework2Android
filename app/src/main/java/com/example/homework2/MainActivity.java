package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    //    private ArrayList<String> names = new ArrayList<>();
//    private ArrayList<String> imageUrls = new ArrayList<>();
    private UserRepository userRepository;

    private List<User> userList = new ArrayList<>();
    private ArrayList<String> userNameAndLastName = new ArrayList<>();
    private ArrayList<String> userMarks = new ArrayList<>();
    private CompositeDisposable compositeDisposable;
    private RecyclerViewAdapter adapter;

    private Button addUserBtn;
    private Button removeUserBtn;
    private EditText editTextName;
    private EditText editTextMark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeRecyclerView();
        initializeViewsAndButtons();
        addButtonListener();
        addRemoveButtonListener();
    }

    public void initializeViewsAndButtons() {
        addUserBtn = findViewById(R.id.button);
        removeUserBtn = findViewById(R.id.buttonRemoveUser);
        editTextName = findViewById(R.id.editTextName);
        editTextMark = findViewById(R.id.editTextMark);
    }

    private void addButtonListener() {
        addUserBtn.setOnClickListener(v -> {
            Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    Editable firstAndLastName = editTextName.getText();
                    Editable grade = editTextMark.getText();
                    if (firstAndLastName != null && grade != null && !firstAndLastName.toString().equals("") && !grade.toString().equals("")) {
                        User user = new User(firstAndLastName.toString(), grade.toString());
                        userRepository.insertAll(user);
                        emitter.onComplete();
                    } else {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Please put something in the edit texts", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }

                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Toast.makeText(MainActivity.this, "USER ADDED", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            loadData();
                        }
                    });
        });
    }

    private void addRemoveButtonListener() {
        removeUserBtn.setOnClickListener(v -> {
            Disposable disposable = Observable.create(new ObservableOnSubscribe<Object>() {
                @Override
                public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                    Editable firstAndLastName = editTextName.getText();
                    if (firstAndLastName != null) {
                        User user = userRepository.findByName(firstAndLastName.toString());
                        if (user != null) {
                            userRepository.delete(user);
//                        userRepository.deleteAllUsers();
                            emitter.onComplete();
                        } else {
                            Handler handler = new Handler(Looper.getMainLooper());
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Please put something valid in the edit texts", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                        }
                    } else {
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Please put something in the edit texts", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    }

                }
            }).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Consumer() {
                        @Override
                        public void accept(Object o) throws Exception {
                            Toast.makeText(MainActivity.this, "USER DELETED", Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }, new Action() {
                        @Override
                        public void run() throws Exception {
                            loadData();
                        }
                    });
        });
    }

    private void initUsers() {
        compositeDisposable = new CompositeDisposable();
        AppDatabase appDatabase = AppDatabase.getInstance(this);
        userRepository = UserRepository.getmInstance(UserDataSource.getInstance(appDatabase.userDao()));
        loadData();
    }

    private void loadData() {
        Disposable disposable = userRepository.getAllUsers()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::onGetAllUsersSucces, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(MainActivity.this, throwable.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
        compositeDisposable.add(disposable);
    }

    private void initUsersNames() {
        for (User user : userList) {
            userNameAndLastName.add(user.getName());
            userMarks.add(user.getMark());
        }
    }

    private void onGetAllUsersSucces(List<User> users) {
        this.userList.clear();
        this.userNameAndLastName.clear();
        this.userMarks.clear();
        this.userList.addAll(users);
        initUsersNames();
        adapter.notifyDataSetChanged();
    }

    private void initializeRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new RecyclerViewAdapter(userNameAndLastName, userMarks, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        initUsers();
    }
}

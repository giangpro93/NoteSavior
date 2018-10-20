package com.example.git.notesavior;

import android.support.annotation.NonNull;

import com.google.firebase.database.*;
import com.google.firebase.storage.*;

import java.util.HashMap;
import java.util.ArrayList;
//import java.util.UUID;

public class FireBaseHandler {
    private FirebaseDatabase database;
    private FirebaseStorage storage;

    private DatabaseReference teachers;
    private DatabaseReference students;
    //private DatabaseReference Tclasses;
    //private DatabaseReference Sclasses;
    private StorageReference pictures;

    private User user;

    private HashMap<String, String> teacherLogins = new HashMap<>();
    private HashMap<String, String> studentLogins = new HashMap<>();
    private ArrayList<Class> Classes = new ArrayList<>();
    private Class res = new Class();

    private static FireBaseHandler init = null;

    public static FireBaseHandler getInstance() {
        if (init == null) {
            init = new FireBaseHandler();
        }
        return init;
    }


    private FireBaseHandler() {
        database = FirebaseDatabase.getInstance();
        teachers = database.getReference("teachers");
        //Tclasses = teachers.child("classes");
        students = database.getReference("students");
        //Sclasses = students.child("classes");

        storage = FirebaseStorage.getInstance();
        pictures = storage.getReference("pictures");
        user = new User();
    }
/*
    private FirebaseHandler(String userId) {
        database = FirebaseDatabase.getInstance();
        teachers = database.getReference("teachers");
        students = database.getReference("students");

        storage = FirebaseStorage.getInstance();
        pictures = storage.getReference("pictures");

        user = new User();
        user.uuid = UUID.fromString(userId);
        user.isTeacher = false;
    }
}*/

    public boolean addTeacher(String username, String password) {
        if (teacherLogins.containsKey(username)) {
            return false;
        }

        DatabaseReference newTeacher = teachers.push();
        newTeacher.child("username").setValue(username);
        newTeacher.child("password").setValue(password);

        return true;
    }

    public boolean addStudent(String username, String password) {
        if (studentLogins.containsKey(username)) {
            return false;
        }

        DatabaseReference newStudent = students.push();
        newStudent.child("username").setValue(username);
        newStudent.child("password").setValue(password);

        return true;
    }

    public void getTeachers() {
        teachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherLogins.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    teacherLogins.put((String) d.child("username").getValue(), (String) d.child("password").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getStudents() {
        students.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                studentLogins.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    studentLogins.put((String) d.child("username").getValue(), (String) d.child("password").getValue());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public boolean checkTeacherLogin(String username, String password) {
        if (teacherLogins.containsKey(username) && teacherLogins.get(username).equals(password)) {
            //user = new User();
            user.username = username;
            user.password = password;
            user.isTeacher = true;


            teachers.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        final String id = d.child("username").getValue().toString();
                        if (id.equals(user.username)) {

                            //Classes.clear();
                            DatabaseReference temp = teachers.child(d.getKey()).child("classes");

                            temp.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot2) {
                                    for (DataSnapshot d2 : dataSnapshot2.getChildren()) {
                                        Class newClass;
                                        newClass = new Class();
                                        newClass.courseName = (String) d2.child("courseName").getValue();
                                        newClass.code = (String) d2.child("code").getValue();
                                        newClass.startTime = Integer.parseInt(d2.child("startTime").getValue().toString());
                                        newClass.endTime = Integer.parseInt(d2.child("endTime").getValue().toString());
                                        Classes.add(newClass);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            return true;
        }
        return false;
    }

    public boolean checkStudentLogin(String username, String password) {
        if (studentLogins.containsKey(username) && studentLogins.get(username).equals(password)) {
            //user = new User();
            user.username = username;
            user.password = password;
            user.isTeacher = false;
            return true;
        }

        return false;
    }

    public boolean addClass(final String courseName, final String code, final int startTime, final int endTime) {
        teachers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    final String id = d.child("username").getValue().toString();
                    if (id.equals(user.username)) {
                        DatabaseReference temp1 = teachers.child(d.getKey()).child("classes").push();
                        temp1.child("courseName").setValue(courseName);
                        temp1.child("code").setValue(code);
                        temp1.child("startTime").setValue(startTime);
                        temp1.child("endTime").setValue(endTime);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return true;
    }

    public Class guessClass(int hour) {
        teachers.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {

                    final String id = d.child("username").getValue().toString();
                    if (id.equals(user.username)) {

                        //Classes.clear();
                        DatabaseReference temp = teachers.child(d.getKey()).child("classes");

                        temp.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot2) {
                                for (DataSnapshot d2 : dataSnapshot2.getChildren()) {
                                    Class newClass;
                                    newClass = new Class();
                                    newClass.courseName = (String) d2.child("courseName").getValue();
                                    newClass.code = (String) d2.child("code").getValue();
                                    newClass.startTime = Integer.parseInt(d2.child("startTime").getValue().toString());
                                    newClass.endTime = Integer.parseInt(d2.child("endTime").getValue().toString());
                                    Classes.add(newClass);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        System.out.println("------------------------------------------");
        System.out.println(Classes.size());

        Class nullclass;
        nullclass = new Class();
        nullclass.code = "";
        for (int i = 0; i<Classes.size(); i++) {
            if (Classes.get(i).startTime<=hour && hour<=Classes.get(i).endTime) return Classes.get(i);
        }
        return nullclass;
    }
}

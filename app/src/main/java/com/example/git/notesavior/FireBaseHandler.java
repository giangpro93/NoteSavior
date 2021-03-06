package com.example.git.notesavior;

import android.icu.text.SymbolTable;
import android.net.sip.SipSession;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.*;
import com.google.firebase.storage.*;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
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

    private HashMap<String, String> teacherLogins = new HashMap<String, String>();
    private HashMap<String, String> studentLogins = new HashMap<String, String>();
    private Vector<Class> ArrayOfClasses = new Vector<Class>();
    private Vector<Integer> numberOfClasses = new Vector<Integer>();
    private Vector<String> teacherUsernames = new Vector<String>();

    private int numberOfTeachers;
    private int count;

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
        newTeacher.child("numberOfClasses").setValue(0);

        user.username = username;
        user.password = password;
        user.isTeacher = true;

        return true;
    }

    public boolean addStudent(String username, String code) {

        count = 0;
        DatabaseReference newStudent = null;
        for (int i=0; i<numberOfTeachers; i++){
            for (int j=count; j<count+numberOfClasses.get(i); j++) {
                if (ArrayOfClasses.get(j).code.equals(code)) {
                    newStudent = students.push();
                    if (newStudent!=null) {
                        newStudent.child("username").setValue(username);
                        newStudent.child("code").setValue(code);
                    }
                    return true;
                }
            }
            count=count+numberOfClasses.get(i);
        }
        return false;
    }

    public void getTeachers() {
        teachers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                teacherLogins.clear();
                ArrayOfClasses.clear();
                teacherUsernames.clear();
                count=0;
                numberOfTeachers=0;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    teacherLogins.put((String) d.child("username").getValue(), (String) d.child("password").getValue());
                    teacherUsernames.add((String) d.child("username").getValue());
                    if (d.child("numberOfClasses").getValue()!=null)
                        numberOfClasses.add(Integer.parseInt(d.child("numberOfClasses").getValue().toString()));
                    else numberOfClasses.add(0);

                    DatabaseReference data = teachers.child(d.getKey()).child("classes");
                    numberOfTeachers++;
                    data.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot2) {
                            for (DataSnapshot d2 : dataSnapshot2.getChildren()) {
                                Class newClass = new Class();
                                if (d2.child("courseName").getValue()!=null) newClass.courseName = d2.child("courseName").getValue().toString();
                                if (d2.child("code").getValue()!=null) newClass.code = d2.child("code").getValue().toString();
                                if (d2.child("startTime").getValue()!=null) newClass.startTime = Integer.parseInt(d2.child("startTime").getValue().toString());
                                if (d2.child("endTime").getValue()!=null) newClass.endTime = Integer.parseInt(d2.child("endTime").getValue().toString());
                                ArrayOfClasses.add(newClass);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError2) {}
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
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
            user.username = username;
            user.password = password;
            user.isTeacher = true;

            return true;
        }
        return false;
    }

    public boolean checkStudentLogin(String username, String password) {
        if (studentLogins.containsKey(username) && studentLogins.get(username).equals(password)) {
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
                    //String id = d.child("username").getValue().toString();
                    if (d.child("username").getValue()!=null)
                    if (d.child("username").getValue().toString().equals(user.username)) {
                        if (d.child("numberOfClasses").getValue()!=null)
                        teachers.child(d.getKey()).child("numberOfClasses").setValue(Integer.parseInt(d.child("numberOfClasses").getValue().toString())+1);

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

        Class nullclass;
        nullclass = new Class();
        nullclass.code = "";
        count = 0;
        for (int i=0; i<numberOfTeachers; i++){
            if (teacherUsernames.get(i).equals(user.username))
            {
                for (int j=count; j<(count+numberOfClasses.get(i)); j++)
                if (j<ArrayOfClasses.size()){
                    if (ArrayOfClasses.get(j).startTime <= hour && hour <= ArrayOfClasses.get(j).endTime) {
                        return ArrayOfClasses.get(j);
                    }
                }
            }
            count=count+numberOfClasses.get(i);
        }
        return nullclass;
    }
}

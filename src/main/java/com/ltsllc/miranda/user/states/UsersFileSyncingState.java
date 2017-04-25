package com.ltsllc.miranda.user.states;

import com.google.gson.reflect.TypeToken;
import com.ltsllc.miranda.State;
import com.ltsllc.miranda.file.SingleFile;
import com.ltsllc.miranda.file.states.SingleFileSyncingState;
import com.ltsllc.miranda.user.User;
import com.ltsllc.miranda.user.UsersFile;
import com.ltsllc.miranda.user.states.UsersFileReadyState;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Clark on 2/11/2017.
 */
public class UsersFileSyncingState extends SingleFileSyncingState {
    private UsersFile usersFile;

    public UsersFileSyncingState(UsersFile usersFile) {
        super(usersFile);
        this.usersFile = usersFile;
    }

    public UsersFile getUsersFile() {
        return usersFile;
    }


    public Type getListType () {
        return new TypeToken<ArrayList<User>>(){}.getType();
    }


    public State getReadyState ()
    {
        return new UsersFileReadyState(getUsersFile());
    }


    public boolean contains (Object o) {
        User otherUser = (User) o;

        for (User user : getUsersFile().getData()) {
            if (user.equals(otherUser))
                return true;
        }

        return false;
    }


    @Override
    public List getData() {
        return getUsersFile().getData();
    }


    @Override
    public String getName() {
        return "users";
    }


    @Override
    public SingleFile getFile() {
        return getUsersFile();
    }
}
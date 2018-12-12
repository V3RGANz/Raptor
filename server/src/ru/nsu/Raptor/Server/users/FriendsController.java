package ru.nsu.Raptor.Server.users;

import ru.nsu.Raptor.Server.Exceptions.IllegalOperationException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class FriendsController {

    //region Containers

    private Map<Integer, Set<Integer>> friends = new ConcurrentHashMap<>();
    private Map<Integer, Set<Integer>> invites = new ConcurrentHashMap<>();

    //endregion

    //region Add

    private void addFriend(int userId, int friendId) {
        add( userId, friendId, friends );
        add(friendId, userId, friends);
    }

    public void addInvite(int userId, int friendId) throws IllegalOperationException {
        Set<Integer> friendsSet = friends.get( userId );
        if (friendsSet != null) {
            if (friendsSet.contains( friendId )) {
                throw new IllegalOperationException( "You are already friends", 403 );
            }
        }
        add( userId, friendId, invites );
    }

    public void acceptInvite(int userId, int friendId) throws IllegalOperationException {
        Set<Integer> idSet = invites.get( userId );
        if (idSet != null) {
            if (idSet.remove( friendId )) {
                addFriend( userId, friendId );
            } else {
                throw new IllegalOperationException( "incorrect id", 403 );
            }
        } else {
            throw new IllegalOperationException( "incorrect id", 403 );
        }
    }

    private void add(int userId, int friendId, Map<Integer, Set<Integer>> map) {
        Set<Integer> friendsSet = map.get( userId );
        if (friendsSet == null) {
            friendsSet = new ConcurrentSkipListSet<>();
        }
        friendsSet.add( friendId );
        map.put( userId, friendsSet );
    }

    //endregion

    //region Remove

    public void declineInvite(int userId, int friendId) throws IllegalOperationException {
        remove( userId, friendId, friends );
    }

    public void removeFriend(int userId, int friendId) throws IllegalOperationException {
        remove( userId, friendId, invites );
    }

    private void remove(int userId, int friendId, Map<Integer, Set<Integer>> map) throws IllegalOperationException {
        Set<Integer> idSet = map.get( userId );
        if (idSet == null) {
            throw new IllegalOperationException( "incorrect id", 403 );
        }
        if (!idSet.remove( friendId )) {
            throw new IllegalOperationException( "incorrect id", 403 );
        }
    }

    //endregion

    //region Get

    public List<Integer> getFriendList(int userId) {
        return getList( userId, friends );
    }

    public List<Integer> getInvitesList(int userId) {
        return getList( userId, invites );
    }

    private List<Integer> getList(int userId, Map<Integer, Set<Integer>> map) {
        List<Integer> list = new ArrayList<>();
        Set<Integer> idSet = map.get( userId );
        if (idSet != null) {
            list.addAll( idSet );
        }
        return list;
    }

    //endregion

    public boolean isFriends(int id1, int id2){
        return friends.get( id1 ).contains( id2 );
    }

    //region Singleton

    private static FriendsController ourInstance = new FriendsController();

    public static FriendsController getInstance() {
        return ourInstance;
    }

    private FriendsController() {
    }

    //endregion
}

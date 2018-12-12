package ru.nsu.Raptor.Server.users;

import ru.nsu.Raptor.Server.Exceptions.AuthException;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class UserController {

    //region Private fields

    private Map<String, String> logins = new ConcurrentHashMap<>();
    private Map<String, Integer> ids = new ConcurrentHashMap<>();
    private AtomicInteger MAX_ID = new AtomicInteger( 0 );

    //endregion

    //region Auth

    public UserSession register(String login, String password) throws AuthException {
        if (logins.containsKey( login )) {
            throw new AuthException( "User already exists", 403 );
        }
        logins.put( login, password );
        int id = MAX_ID.getAndIncrement();
        ids.put( login, id );
        return login( login, password );
    }

    public UserSession login(String login, String password) throws AuthException {
        String tmppass = logins.get( login );
        if (tmppass == null) {
            throw new AuthException( "Incorrect login or password", 403 );
        }
        if (!tmppass.equals( password )) {
            throw new AuthException( "Incorrect login or password", 403 );
        }
        int id = ids.get( login );
        UUID token = tokenController.createToken( id );
        UserSession session = new UserSession();
        session.id = id;
        session.token = token.toString();
        return session;
    }

    //endregion

    public boolean check(int id, String name) {
        Integer tmpid = ids.get( name );
        return tmpid != null && tmpid == id;
    }

    //region Singleton

    private TokenController tokenController = TokenController.getInstance();

    private static UserController ourInstance = new UserController();

    public static UserController getInstance() {
        return ourInstance;
    }

    private UserController() {
    }

    //endregion
}

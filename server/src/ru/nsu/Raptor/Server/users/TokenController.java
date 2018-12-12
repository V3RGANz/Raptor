package ru.nsu.Raptor.Server.users;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TokenController {

    private Map<UUID, Integer> tokens = new ConcurrentHashMap<>();

    public boolean tokenValid(int id, UUID token){
        Integer checkId = tokens.get( token );
        return checkId == id;
    }

    UUID createToken(int id){
        UUID token = UUID.randomUUID();
        while (tokens.containsKey( token )){
            token = UUID.randomUUID();
        }
        tokens.put( token,id );
        return token;
    }

    //region Singleton

    private static TokenController ourInstance = new TokenController();

    public static TokenController getInstance() {
        return ourInstance;
    }

    private TokenController() {
    }

    //endregion
}

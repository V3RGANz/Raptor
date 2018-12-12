package ru.nsu.Raptor.Server.services;

import ru.nsu.Raptor.Server.Exceptions.AuthException;
import ru.nsu.Raptor.Server.Exceptions.IllegalOperationException;
import ru.nsu.Raptor.Server.users.FriendsController;
import ru.nsu.Raptor.Server.users.TokenController;
import ru.nsu.Raptor.Server.users.UserController;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/")
public class UserRESTfulService {

    //region Controllers

    private UserController userController = UserController.getInstance();
    private TokenController tokenController = TokenController.getInstance();
    private FriendsController friendsController = FriendsController.getInstance();

    //endregion

    // region Auth

    @POST
    @Path("login")
    //returns UserSession
    public Response login(@HeaderParam("login") String login, @HeaderParam("password") String password) {

        if (login == null || password == null) {
            return Response.status( 400 ).entity( "There is no login or password" ).build();
        }

        try {
            return Response.status( 200 )
                    .entity( userController.login( login, password ) )
                    .build();
        } catch (AuthException e) {
            return Response.status( e.getErrorCode() )
                    .entity( e.toString() )
                    .build();
        }
    }

    @Path("register")
    @POST
    //returns UserSession
    public Response register(@HeaderParam("login") String login, @HeaderParam("password") String password) {

        if (login == null || password == null) {
            return Response.status( 400 ).entity( "There is no login or password" ).build();
        }

        try {
            return Response.status( 200 )
                    .entity( userController.register( login, password ) )
                    .build();
        } catch (AuthException e) {
            return Response.status( e.getErrorCode() )
                    .entity( e.toString() )
                    .build();
        }
    }

    //endregion

    //region Friends

    @Path("users/{my_id}/friends")
    @GET
    //returns list of friends
    public Response getFriends(@PathParam("my_id") int id, @HeaderParam("token") String token) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (tokenController.tokenValid( id, UUID.fromString( token ) )) {
            return Response.status( 200 ).entity( friendsController.getFriendList( id ) ).build();
        } else return Response.status( 449 ).entity( "new token" ).build();
    }

    @Path("users/{my_id}/friends/{friend_id}")
    @DELETE
    public Response deleteFriends(@PathParam("my_id") int selfID, @PathParam("friend_id") int friendID,
                                  @HeaderParam("token") String token) {
        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (!tokenController.tokenValid( selfID, UUID.fromString( token ) )) {
            return Response.status( 449 ).entity( "new token" ).build();
        }

        try {
            friendsController.removeFriend( selfID, friendID );
            return Response.status( 204 ).build();
        } catch (IllegalOperationException e) {
            return Response.status( e.getErrorCode() ).entity( e.toString() ).build();
        }
    }

    //endregion

    //region Invites

    @Path("users/{friend_id}/invites/{my_id}")
    @POST
    public Response postInvite(@PathParam("friend_id") int friendID, @PathParam("my_id") int selfID,
                               @HeaderParam("token") String token) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (!tokenController.tokenValid( selfID, UUID.fromString( token ) )) {
            return Response.status( 449 ).entity( "new token" ).build();
        }

        try {
            friendsController.addInvite( friendID, selfID );
            return Response.status( 204 ).build();
        } catch (IllegalOperationException e) {
            return Response.status( e.getErrorCode() ).entity( e.toString() ).build();
        }
    }

    @Path("users/{my_id}/invites")
    @GET
    //@Produces(MediaType.APPLICATION_JSON)
    //returns list of invites
    public Response getInvites(@PathParam("my_id") int id,
                               @HeaderParam("token") String token) {
        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (!tokenController.tokenValid( id, UUID.fromString( token ) )) {
            return Response.status( 449 ).entity( "new token" ).build();
        }

        return Response.status( 200 ).entity( friendsController.getInvitesList( id ) ).build();
    }

    @Path("users/{my_id}/invites/{friend_id}")
    @PUT
    public Response putInvite(@PathParam("friend_id") int friendID, @PathParam("my_id") int selfID,
                              @HeaderParam("option") String option, @HeaderParam("token") String token) {
        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (option == null) {
            return Response.status( 400 ).entity( "There is no option" ).build();
        }

        if (!tokenController.tokenValid( selfID, UUID.fromString( token ) )) {
            return Response.status( 449 ).entity( "new token" ).build();
        }

        try {
            switch (option) {
                case "accept":
                    friendsController.acceptInvite( selfID, friendID );
                    break;
                case "decline":
                    friendsController.declineInvite( selfID, friendID );
                    break;
                default:
                    return Response.status( 405 ).entity( "Wrong option" ).build();
            }
        } catch (IllegalOperationException e) {
            return Response.status( e.getErrorCode() ).entity( e.toString() ).build();
        }
        return Response.status( 204 ).build();
    }

    //endregion

}

package ru.nsu.Raptor.Server.services;

import ru.nsu.Raptor.Server.notifications.UtilNotification;
import ru.nsu.Raptor.Server.Exceptions.IllegalOperationException;
import ru.nsu.Raptor.Server.notifications.IncomingNotificationController;
import ru.nsu.Raptor.Server.notifications.Notification;
import ru.nsu.Raptor.Server.notifications.NotificationController;
import ru.nsu.Raptor.Server.users.FriendsController;
import ru.nsu.Raptor.Server.users.TokenController;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/")
public class NotificationRESTfulService {

    //region Controllers

    private IncomingNotificationController incomingNotificationController = IncomingNotificationController.getInstance();
    private NotificationController outgoingNotificationController = NotificationController.getInstance();
    private TokenController tokenController = TokenController.getInstance();
    private FriendsController friendsController = FriendsController.getInstance();

    //endregion

    //region Incoming notifications

    @Path("users/{my_id}/notifications/incoming")
    @GET
    //returns list of incoming notifications
    public Response getIncomingNotifications(@PathParam("my_id") int id, @HeaderParam("token") String token) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (tokenController.tokenValid( id, UUID.fromString( token ) )) {
            return Response.status( 200 )
                    .entity( incomingNotificationController.getNotifications( id ) )
                    .build();
        } else {
            return Response.status( 449 ).entity( "new token" ).build();
        }
    }

    @Path("users/{my_id}/notifications/incoming/{notification_id}")
    @PUT
    public Response updateIncomingNotification(@PathParam("my_id") int userId,
                                               @PathParam("notification_id") int notificationId,
                                               @HeaderParam("token") String token) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (tokenController.tokenValid( userId, UUID.fromString( token ) )) {
            try {
                incomingNotificationController.markReadNotification( userId, notificationId );
                return Response.status( 204 ).build();
            } catch (IllegalOperationException e) {
                return Response.status( e.getErrorCode() ).entity( e.toString() ).build();
            }
        } else {
            return Response.status( 449 ).entity( "new token" ).build();
        }
    }

    @Path("users/{my_id}/notifications/incoming/{notification_id}")
    @DELETE
    public Response deleteIncomingNotification(@PathParam("my_id") int userId,
                                               @PathParam("notification_id") int notificationId,
                                               @HeaderParam("token") String token) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (tokenController.tokenValid( userId, UUID.fromString( token ) )) {
            incomingNotificationController.deleteNotification( userId, notificationId );
            return Response.status( 200 ).build();
        } else {
            return Response.status( 449 ).entity( "new token" ).build();
        }

    }

    //endregion

    //region Outgoing notifications

    @Path("users/{my_id}/notifications/outgoing")
    @GET
    //returns list of notifications
    public Response getOutgoingNotifications(@PathParam("my_id") int id, @HeaderParam("token") String token) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (tokenController.tokenValid( id, UUID.fromString( token ) )) {
            return Response.status( 200 ).entity( outgoingNotificationController.getNotifications( id ) ).build();
        } else {
            return Response.status( 449 ).entity( "new token" ).build();
        }
    }

    @Path("users/{my_id}/notifications/outgoing/{notification_id}")
    @DELETE
    public Response deleteOutgoingNotification(@PathParam("my_id") int userId,
                                           @PathParam("notification_id") int notificationId,
                                           @HeaderParam("token") String token) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if (tokenController.tokenValid( userId, UUID.fromString( token ) )) {
            outgoingNotificationController.deleteNotification( userId, notificationId );
            return Response.status( 204 ).build();
        } else {
            return Response.status( 449 ).entity( "new token" ).build();
        }

    }

    @Path("users/{my_id}/notifications/outgoing")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response sendOutgoingNotification(@PathParam("my_id") int userId, @HeaderParam("receiver_id") Integer recieverID,
                                         @HeaderParam("token") String token, Notification notification) {

        if (token == null) {
            return Response.status( 401 ).entity( "There is no token" ).build();
        }

        if(recieverID == null){
            return Response.status( 400 ).entity( "There is no receiver" ).build();
        }

        if(!friendsController.isFriends( userId,recieverID )){
            return Response.status( 403 ).entity( "You are not friends" ).build();
        }
        if (tokenController.tokenValid( userId, UUID.fromString( token ) )) {
            UtilNotification util = new UtilNotification( notification );
            outgoingNotificationController.addNotification( userId, util );
            incomingNotificationController.addNotification( recieverID, util );
            return Response.status( 204 ).build();
        } else {
            return Response.status( 449 ).entity( "new token" ).build();
        }
    }

    //endregion
}

package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class SQLSecurityDAO extends SQLConnection implements ISecurityDAO {

    @Override
    public void addUser(String userId) {
        connectToDatabase();
        try {
            String sql = Queries.getInstance().getQuery("addUserQuery");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public void savePublicKey(String userId, String publicKey) {
        connectToDatabase();
        try {
            String sql = Queries.getInstance().getQuery("savePublicKeyQuery");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, publicKey);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public String getOtherPublicKey(String userId, String chatId) {
        connectToDatabase();
        try {
            String sql = Queries.getInstance().getQuery("getOtherPublicKeyQuery");
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, chatId);
            statement.setString(2, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (Exception e){
            throw new DatabaseRequestException(e);
        }
    }
}

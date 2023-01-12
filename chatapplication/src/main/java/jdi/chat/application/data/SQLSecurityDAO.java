package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

import java.sql.*;

public class SQLSecurityDAO implements ISecurityDAO {

    @Override
    public void addUser(String userId) {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("addUserQuery");
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }
    }

    @Override
    public String savePublicKey(String userId, String publicKey) throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("savePublicKeyQuery");
        ResultSet resultSet = null;
        try (CallableStatement statement = SQLConnection.connection.prepareCall(sql)) {
            statement.setString(1, publicKey);
            statement.setString(2, userId);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        } finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
    }

    @Override
    public String getOtherPublicKey(String userId, String chatId) throws SQLException {
        SQLConnection.connectToDatabase();
        String sql = Queries.getInstance().getQuery("getOtherPublicKeyQuery");
        ResultSet resultSet = null;
        try (PreparedStatement statement = SQLConnection.connection.prepareStatement(sql)) {
            statement.setString(1, chatId);
            statement.setString(2, userId);
            resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (Exception e) {
            throw new DatabaseRequestException(e);
        }finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
    }
}

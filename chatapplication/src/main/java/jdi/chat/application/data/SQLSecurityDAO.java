package jdi.chat.application.data;

import jdi.chat.application.data.exceptions.DatabaseRequestException;
import jdi.chat.application.util.files.Queries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SQLSecurityDAO extends AbstractSecurityDAO{
    @Override
    public void addUser(String userId) {
        try {
            String sql = Queries.getInstance().getQuery("addUserQuery");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    @Override
    public void savePublicKey(String userId, String publicKey) {
        try {
            String sql = Queries.getInstance().getQuery("savePublicKeyQuery");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, publicKey);
            statement.setString(2, userId);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DatabaseRequestException();
        }
    }

    @Override
    public String getOtherPublicKey(String userId, String chatId) {
        try {
            String sql = Queries.getInstance().getQuery("getOtherPublicKeyQuery");
            PreparedStatement statement = ConnectionDAO.getInstance().getConnection().prepareStatement(sql);
            statement.setString(1, chatId);
            statement.setString(2, userId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (Exception e){
            throw new DatabaseRequestException();
        }
    }
}

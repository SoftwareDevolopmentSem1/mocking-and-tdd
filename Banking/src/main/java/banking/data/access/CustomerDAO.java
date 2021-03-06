package banking.data.access;

import banking.Account;
import banking.Bank;
import banking.Customer;
import banking.RealCustomer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public static Customer get(String id) {
        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM get_customer(?)");
            stm.setString(1, id);
            ResultSet rs = stm.executeQuery();

            if (rs.next()) {
                String cpr = rs.getString(1);
                String name = rs.getString(2);
                String bank_cvr = rs.getString(3);

                Bank bank = BankDAO.get(bank_cvr);
                return new RealCustomer(bank, cpr, name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Customer> getAll() {
        List<Customer> customers = new ArrayList<Customer>();

        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("SELECT * FROM customers");
            ResultSet rs = stm.executeQuery();

            while (rs.next()) {
                String cpr = rs.getString(1);
                String name = rs.getString(2);
                String bank_cvr = rs.getString(3);

                Bank bank = BankDAO.get(bank_cvr);
                customers.add(new RealCustomer(bank, cpr, name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static int add(String cpr, String name, String cvr) {
        try {
            Connection con = DBConnector.getConnection();
            PreparedStatement stm = con.prepareStatement("CALL add_customer(?, ?, ?)");
            stm.setString(1, cpr);
            stm.setString(2, name);
            stm.setString(3, cvr);
            return stm.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


}

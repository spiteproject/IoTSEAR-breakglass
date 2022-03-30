package be.hcbgsystem.core.data;

import be.hcbgsystem.core.models.emergencycontact.EmergencyContact;
import be.hcbgsystem.core.models.policies.breakglass.BreakGlassLevel;
import be.hcbgsystem.core.models.policies.emergency.EmergencyLevel;

import java.sql.*;

import java.util.ArrayList;

public class BreakGlassDB implements BreakGlassSystemStatus, EmergencySystemStatus, EmergencyContactsCRUD {
    private final static String url = "jdbc:mysql://localhost:3306/hcbgsystem";
    private static Connection conn;

    private static String BREAK_GLASS_LEVEL = "break_glass_level";
    private static String EMERGENCY_LEVEL = "emergency_level";

    private static BreakGlassLevel bgLevel = new BreakGlassLevel(0);
    private static EmergencyLevel emergencyLevel = new EmergencyLevel(0);

    static {
        try {
            conn = DriverManager.getConnection(url, "root", "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getSystemStateVarValue(String var) {
        try {
            PreparedStatement stmt = conn.prepareStatement("SELECT Value FROM state WHERE Var = ?");
            stmt.setString(1, var);
            ResultSet set = stmt.executeQuery();
            set.absolute(1);
            return set.getString("value");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setSystemStateVarValue(String var, String value) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE State SET Value = ? WHERE Var = ?");
            stmt.setString(1, value);
            stmt.setString(2, var);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public BreakGlassLevel getBreakGlassLevel() {
        return new BreakGlassLevel(Integer.parseInt(getSystemStateVarValue(BREAK_GLASS_LEVEL)));
    }

    @Override
    public void setBreakGlassLevel(BreakGlassLevel level) {
       setSystemStateVarValue(BREAK_GLASS_LEVEL, Integer.toString(level.getLevel()));
    }

    @Override
    public EmergencyLevel getEmergencyLevel() {
       return new EmergencyLevel(Integer.parseInt(getSystemStateVarValue(EMERGENCY_LEVEL)));
    }

    @Override
    public void setEmergencyLevel(EmergencyLevel level) {
        setSystemStateVarValue(EMERGENCY_LEVEL, Integer.toString(level.getLevel()));
    }

    @Override
    public void addEmergencyContact(EmergencyContact contact) {
        try {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO EmergencyContacts (FirstName, LastName, Email, Telephone, Role) VALUES (?, ?, ?, ?, ?)");
            stmt.setString(1, contact.getFirstName());
            stmt.setString(2, contact.getLastName());
            stmt.setString(3, contact.getEmail());
            stmt.setString(4, contact.getTelephone());
            stmt.setString(5, contact.getRole());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmergencyContact(int id) {
        try {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM EmergencyContacts WHERE Id = ?");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmergencyContact(int id, EmergencyContact newContact) {
        try {
            PreparedStatement stmt = conn.prepareStatement("UPDATE EmergencyContacts SET FirstName = ?, LastName = ?, Email = ?, Telephone = ?, Role = ? WHERE Id = ?");
            stmt.setString(1, newContact.getFirstName());
            stmt.setString(2, newContact.getLastName());
            stmt.setString(3, newContact.getEmail());
            stmt.setString(4, newContact.getTelephone());
            stmt.setString(5, newContact.getRole());
            stmt.setInt(6, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<EmergencyContact> getEmergencyContactsWithRole(String role, boolean all) {
        try {
            PreparedStatement stmt;
            if (all) {
                stmt = conn.prepareStatement("SELECT * FROM EmergencyContacts");
            } else {
                stmt = conn.prepareStatement("SELECT * FROM EmergencyContacts WHERE role = ?");
                stmt.setString(1, role);
            }
            ResultSet set = stmt.executeQuery();
            ArrayList<EmergencyContact> res = new ArrayList<>();
            
            while (set.next()) {
                res.add(new EmergencyContact(set.getInt("Id"), set.getString("FirstName"), set.getString("LastName"), set.getString("Email"), set.getString("Telephone"), set.getString("Role")));
            }

            return res;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<EmergencyContact> getEmergencyContacts() {
        return getEmergencyContactsWithRole("", true);
    }

    @Override
    public ArrayList<EmergencyContact> getPatientContacts() {
        return getEmergencyContactsWithRole("patient", false);
    }

    public static void main(String[] args) {
        BreakGlassDB db = new BreakGlassDB();

        db.setBreakGlassLevel(new BreakGlassLevel(99));
        db.setEmergencyLevel(new EmergencyLevel(99));
        BreakGlassLevel newBreakGlassLevel = db.getBreakGlassLevel();
        EmergencyLevel newEmergencyLevel = db.getEmergencyLevel();
        assert(newBreakGlassLevel.getLevel() == 99);
        assert(newEmergencyLevel.getLevel() == 99);
        db.setBreakGlassLevel(new BreakGlassLevel(0));
        db.setEmergencyLevel(new EmergencyLevel(0));
        assert(newBreakGlassLevel.getLevel() == 0);
        assert(newEmergencyLevel.getLevel() == 0);
    }
}

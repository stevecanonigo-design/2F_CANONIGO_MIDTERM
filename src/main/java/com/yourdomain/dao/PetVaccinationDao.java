package com.yourdomain.dao;

import com.yourdomain.model.PetVaccinationEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetVaccinationDao {

    public boolean insert(PetVaccinationEntity p) {
        String sql = "INSERT INTO pets (pet_name, species, vaccine, fee) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.petName());
            ps.setString(2, p.species());
            ps.setString(3, p.vaccine());
            ps.setDouble(4, p.fee());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Insert error: " + e.getMessage());
            return false;
        }
    }

    public List<PetVaccinationEntity> findAll() {
        List<PetVaccinationEntity> list = new ArrayList<>();
        String sql = "SELECT id, pet_name, species, vaccine, fee FROM pets ORDER BY id";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new PetVaccinationEntity(
                        rs.getInt("id"),
                        rs.getString("pet_name"),
                        rs.getString("species"),
                        rs.getString("vaccine"),
                        rs.getDouble("fee")));
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return list;
    }
}

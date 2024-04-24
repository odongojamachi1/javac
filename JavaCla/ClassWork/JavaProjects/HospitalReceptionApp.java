

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class HospitalReceptionApp extends JFrame {

    private List<Patient> patients;      

    public HospitalReceptionApp() {
        setTitle("WANMIC INTERNATIONAL HOSPITAL");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the frame on screen

        patients = new ArrayList<>();

        // Create components
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("WANMIC INTERNATIONAL HOSPITAL");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        JButton addPatientButton = new JButton("Add Patient");
        JButton viewPatientsButton = new JButton("View Patients");
        JButton editPatientButton = new JButton("Edit Patient");
        JButton deletePatientButton = new JButton("Delete Patient");

        buttonPanel.add(addPatientButton);
        buttonPanel.add(viewPatientsButton);
        buttonPanel.add(editPatientButton);
        buttonPanel.add(deletePatientButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // Add main panel to frame
        add(mainPanel);

        // Handle button clicks
        addPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPatientDialog();
            }
        });

        viewPatientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (patients.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No patients recorded.");
                } else {
                    displayPatients();
                }
            }
        });

        editPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (patients.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No patients available to edit.");
                } else {
                    editPatientDialog();
                }
            }
        });

        deletePatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (patients.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No patients available to delete.");
                } else {
                    deletePatientDialog();
                }
            }
        });
    }

    private void addPatientDialog() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));

        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();

        panel.add(new JLabel("Patient Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Patient Age:"));
        panel.add(ageField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add New Patient",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String ageText = ageField.getText().trim();

            if (!name.isEmpty() && isInteger(ageText)) {
                int age = Integer.parseInt(ageText);
                patients.add(new Patient(name, age));
                JOptionPane.showMessageDialog(null, "Patient added successfully: " + name);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid name and age.");
            }
        }
    }
       //this displays patient ditails 
    private void displayPatients() {
        StringBuilder message = new StringBuilder("Patients:\n");
        //for loop
        for (Patient patient : patients) {
            message.append(patient.getName()).append(" - Age: ").append(patient.getAge()).append("\n");
        }
        JOptionPane.showMessageDialog(null, message.toString());
    }
        //this is for editing
    private void editPatientDialog() {
        String[] patientNames = patients.stream()
                .map(Patient::getName)
                .toArray(String[]::new);

        String selectedPatient = (String) JOptionPane.showInputDialog(null,
                "Select a patient to edit:", "Edit Patient",
                JOptionPane.QUESTION_MESSAGE, null, patientNames, null);

        if (selectedPatient != null) {
            String newName = JOptionPane.showInputDialog("Enter new name:");
            String newAgeStr = JOptionPane.showInputDialog("Enter new age:");

            if (newName != null && isInteger(newAgeStr)) {
                int newAge = Integer.parseInt(newAgeStr);
                Patient editedPatient = patients.stream()
                        .filter(p -> p.getName().equals(selectedPatient))
                        .findFirst()
                        .orElse(null);

                if (editedPatient != null) {
                    editedPatient.setName(newName);
                    editedPatient.setAge(newAge);
                    JOptionPane.showMessageDialog(null, "Patient information updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Patient not found.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid name and age.");
            }
        }
    }

    //this is for deleting
    private void deletePatientDialog() {
        String[] patientNames = patients.stream()
                .map(Patient::getName)
                .toArray(String[]::new);

        String selectedPatient = (String) JOptionPane.showInputDialog(null,
                "Select a patient to delete:", "Delete Patient",
                JOptionPane.QUESTION_MESSAGE, null, patientNames, null);

                //this is were the logic relais 
        if (selectedPatient != null) {
            patients.removeIf(p -> p.getName().equals(selectedPatient));
            JOptionPane.showMessageDialog(null, "Patient deleted successfully.");
        }
    }

    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HospitalReceptionApp app = new HospitalReceptionApp();
            app.setVisible(true);
        });
    }
}

//these are patiant classes

class Patient {
    private String name;
    private int age;

    public Patient(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}



// file hand
//display the regi users from the file
//graphical work
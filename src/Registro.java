import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class Registro {

    private JTextField txtTarifa;
    private JLabel lblTipo;
    private JLabel lblMarca;
    private JLabel lblModelo;
    private JLabel lblPlaca;
    private JLabel lblFecha;
    private JComboBox cbxTipo;
    private JTextField txtMarca;
    private JTextField txtModelo;
    private JTextField txtPlaca;
    private JTextField txtFechaI;
    private JTextField txtHoraI;
    private JLabel lblFsalida;
    private JLabel lblHsalida;
    private JTextField txtFechaS;
    private JTextField txtHoraS;
    private JButton btnCalcular;
    JPanel pnlMain;

    public Registro() {
        txtTarifa.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!validarTarifa(txtMarca.getText())) {
                    JOptionPane.showMessageDialog(new JFrame(), "La tarifa no contiene letras");
                    if (txtTarifa.getText().contains("."));
                    JOptionPane.showMessageDialog(new JFrame(), "Digite la tarifa sin puntos ni comas");
                }
            }
        });
        cbxTipo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String opcion = cbxTipo.getSelectedItem().toString();
                if (opcion.equals("Tipo de Vehículo")){
                    JOptionPane.showMessageDialog(new JFrame(), "Elija un tipo de vehículo");
                }
            }
        });
        txtMarca.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!validarString(txtMarca.getText())) {
                    JOptionPane.showMessageDialog(new JFrame(), "El nombre de la marca comienza en mayúscula");
                }
            }
        });
        txtModelo.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!validarString(txtModelo.getText())) {
                    JOptionPane.showMessageDialog(new JFrame(), "El nombre del modelo empieza en mayúscula");
                }
            }
        });
        txtPlaca.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!validarPlaca(txtPlaca.getText())) {
                    JOptionPane.showMessageDialog(new JFrame(), "La placa contiene 3 letras mayúsculas y 3 numeros");
                }
            }
        });
        txtFechaI.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isValidFormat(txtFechaI.getText()));
            }
        });
        txtHoraI.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!validTime(txtHoraI.getText()));
            }
        });
        txtFechaS.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!isValidFormat(txtFechaI.getText()));
            }
        });
        txtHoraS.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (!validTime(txtHoraS.getText()));
            }
        });
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double tiempototal = 0 ;
                if(!cbxTipo.getSelectedItem().equals("Tipo de Vehículo")&&!txtMarca.getText().isEmpty()&&!txtModelo.getText().isEmpty()&&
                    !txtPlaca.getText().isEmpty()&&!txtFechaI.getText().isEmpty()&&!txtHoraI.getText().isEmpty()&&
                    !txtFechaS.getText().isEmpty()&&!txtHoraS.getText().isEmpty()&&!txtTarifa.getText().isEmpty()){
                    try {
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
                        Date firstDate = format.parse(txtFechaI.getText());
                        Date secondDate = format.parse(txtFechaS.getText());
                        Date firstHour = format2.parse(txtHoraI.getText());
                        Date secondHour = format2.parse(txtHoraS.getText());
                        float pay = 0;
                        float payT = Float.parseFloat(txtTarifa.getText());

                        if(firstHour.getTime() >= secondHour.getTime()){
                            long timeD = Math.abs(secondHour.getTime() - firstHour.getTime());
                            long days = TimeUnit.HOURS.convert(timeD, TimeUnit.MILLISECONDS);

                            long timeDiff = Math.abs(secondDate.getTime()-firstDate.getTime());
                            long days2 = TimeUnit.HOURS.convert(timeDiff, TimeUnit.MILLISECONDS);

                            tiempototal = days2 - days;

                            pay = (float) (tiempototal * payT);

                        }else {
                            long timeD = Math.abs(secondHour.getTime() - firstHour.getTime());
                            long days = TimeUnit.HOURS.convert(timeD, TimeUnit.MILLISECONDS);

                            long timeDiff = Math.abs(secondDate.getTime()-firstDate.getTime());
                            long days2 = TimeUnit.HOURS.convert(timeDiff, TimeUnit.MILLISECONDS);

                            tiempototal = days + days2;
                            pay = (float) (tiempototal * payT);
                        }

                        JOptionPane.showMessageDialog(new JFrame(), "Tipo de vehículo: " + cbxTipo.getSelectedItem() +"\nMarca: " + txtMarca.getText() + "\nModelo: " +txtModelo.getText()
                                + "\nPlaca: " + txtPlaca.getText() +"\nFecha de Ingreso: " + txtFechaI.getText() + "\nHora de ingreso: " + txtHoraI.getText()
                                + "\nFecha de Salida: " + txtFechaS.getText() + "\nHora de salida: " + txtHoraS.getText() + "\nTotal de horas en el parqueadero: " + tiempototal + "\nEl precio que de be pagar es: " + pay +
                                 " mil");
                        }catch (Exception ex){
                        JOptionPane.showMessageDialog(new JFrame(), "Error: " + ex.getMessage());

                    }
                } else {
                    JOptionPane.showMessageDialog(new JFrame(), "Rellene todos los campos");
                }
            }
        });
    }

    private static boolean isValidFormat(String date) {
        Date myDate = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            myDate = format.parse(date);
            if (!date.equals(format.format(myDate))) {
                myDate = null;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Error: " + e.getMessage());
        }
        if (myDate == null) {
            return false;
        } else {
            return true;
        }
    }
    public static boolean validarTarifa(String tarifa){

        return tarifa.matches("^([0-9])$");
    }
    public static boolean validarString(String nombre){

        return nombre.matches("^([A-Z]{1}[a-z]+[ ]*){1,2}$");
    }

    public static boolean validarPlaca(String placa){

        return placa.matches("^([A-Z]{3}[0-9]{3})$");
    }

    private static boolean validTime(String time) {
        Date myTime = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
            myTime = format.parse(time);
            if (!time.equals(format.format(myTime))) {
                myTime = null;
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Error: " + e.getMessage());
        }
        if (myTime == null) {
            return false;
        } else {
            return true;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        RegistroVacunas ventana = new RegistroVacunas();
        ventana.setVisible(true);

        ventana.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                ventana.guardarRegistros();
                System.exit(0);
            }
        });
    }
}

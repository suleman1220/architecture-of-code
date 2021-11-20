
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author DELL Inspiron 15
 */
public class Details extends javax.swing.JFrame {

    /**
     * Creates new form Details
     */
    public Details() {
        initComponents();
    }
    
    void scanFile(File f){
            String methods[] = new String[70];
            String variables[] = new String[70];
            String typeOfMethod[] = new String[70];
            String typeOfVar[] = new String[70];
            int methodIndex = 0, variableIndex = 0;
            output.append("\nName of Class: " + f.getName().substring(0, f.getName().lastIndexOf('.')));
            output.append("\nType of Class: " + f.getName().substring(f.getName().lastIndexOf('.'), f.getName().length()));
            
            try {
                BufferedReader br = new BufferedReader(new FileReader(f));
                String line = br.readLine();
                output.append("\n\n");
            while (line != null) {
               if(line.endsWith(")") || line.endsWith("){") || line.endsWith(") {")){
                   String arr[] = line.split(" ");
                   for(int i = 0; i < arr.length; i++){
                       if(arr[i].contains("(") && !arr[i].contains(".")){
                           String method = arr[i].substring(0, arr[i].indexOf("(")) + "()";
                           String type;
                           if(i == 0){
                               type = "void";
                           } else {
                               type = arr[i-1];
                           }
                           boolean contains = false;
                           for(int j = 0; j < methodIndex; j++){
                               if(methods[j].equals(method)){
                                   contains = true;
                                   break;
                               }
                           }
                           if(!contains && !method.equals("()")){
                               methods[methodIndex++] = method;
                               typeOfMethod[methodIndex-1] = type;
                           }
                           break;
                       }
                   }
               } else if(line.contains("int ") || line.contains("boolean ") || line.contains("float ") || line.contains("char ") || line.contains("double ")){
                   String arr[] = line.split(" ");
                   for(int i = 0; i < arr.length; i++){
                       if(arr[i].contains("int") || arr[i].contains("boolean") || arr[i].contains("float") || arr[i].contains("char") || arr[i].contains("double")){
                           String variable = arr[i+1];
                           String type = arr[i];
                           int indexOfVariable = i+1;
                           while(variable.contains(",")){
                               boolean containsVar = false;
                               for(int j = 0; j < variableIndex; j++){
                                   if(variables[j].equals(variable)){
                                       containsVar = true;
                                       break;
                                   }
                               }
                               if(!containsVar){
                                   variables[variableIndex++] = variable;
                                   typeOfVar[variableIndex-1] = type;
                               }
                               if(indexOfVariable+1 < arr.length){
                                   variable = arr[++indexOfVariable];
                               } else {
                                   break;
                               }
                           }
                           boolean containsVar = false;
                           for(int j = 0; j < variableIndex; j++){
                                   if(variables[j].equals(variable)){
                                       containsVar = true;
                                       break;
                                   }
                               }
                           if(!containsVar){
                               variables[variableIndex++] = variable;
                               typeOfVar[variableIndex-1] = type;
                           }
                           break;
                       }
                   }
               }
               line = br.readLine();
            }
            
            boolean containsMain = false;
            for(int i = 0; i < methodIndex; i++){
                if(methods[i].equals("main()")){
                    containsMain = true;
                    break;
                }
            }
            
            for(int i = 0; i < methodIndex; i++){
                String newMethod = methods[i].replaceAll("\t", "");
                newMethod = newMethod.replaceAll(" ", "");
                methods[i] = newMethod;
            }
            
            for(int i = 0; i < methodIndex; i++){
                String newMethod = typeOfMethod[i].replaceAll("\t", "");
                newMethod = newMethod.replaceAll(" ", "");
                typeOfMethod[i] = newMethod;
            }
            
            int builtInMethods = 0;
            for(int i = 0; i < methodIndex; i++){
                if(methods[i].equals("if()") || methods[i].equals("while()") || methods[i].equals("for()") || methods[i].equals("switch()")){
                    builtInMethods++;
                }
            }
            
            output.append("Total Methods: " + (containsMain ? ((methodIndex - 1 - builtInMethods) +  " other methods and 1 main method") : methodIndex - builtInMethods));
            output.append("\nName of Methods:\n");
            int methodNumber = 1;
            for(int i = 0; i < methodIndex; i++){
                if(methods[i].equals("if()") || methods[i].equals("while()") || methods[i].equals("for()") || methods[i].equals("switch()")){
                    continue;
                }
                output.append("\t" + methodNumber + ". " + typeOfMethod[i] + " " + methods[i] + "\n");
                methodNumber++;
            }
            
            int functionDeclarations = 0;
            for(int i = 0; i < variableIndex; i++){
                if(variables[i].contains(",") || variables[i].contains(";") || variables[i].contains(")")){
                    String newVar;
                    newVar = variables[i].replace(",", "");
                    newVar = newVar.replace(";", "");
                    newVar = newVar.replace(")", "");
                    variables[i] = newVar;
                }
                
                if(variables[i].contains("(") || variables[i].contains("\"") || variables[i].equals("=")){
                    functionDeclarations++;
                }
            }
            
            for(int i = 0; i < variableIndex; i++){
                String newVar = typeOfVar[i].replaceAll("\t", "");
                newVar = newVar.replaceAll(" ", "");
                typeOfVar[i] = newVar;
            }
            
            output.append("\nTotal Variables: " + (variableIndex - functionDeclarations));
            output.append("\nName of Variables:\n");
            int varNum = 1;
            for(int i = 0; i < variableIndex; i++){
                if(variables[i].contains("(") || variables[i].contains("\"") || variables[i].equals("=")){
                    continue;
                }
                output.append("\t" + varNum + ". " + typeOfVar[i] + " " + variables[i] + "\n");
                varNum++;
            }
            
            output.append("\n---------------------------------------------------------------------------------------------------------------\n");
            
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        pathField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        chooseFileBtn = new javax.swing.JButton();
        submitBtn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        output = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(34, 34, 34));

        jLabel1.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(240, 244, 248));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Architecture Of Code");

        pathField.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        pathField.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jLabel2.setFont(new java.awt.Font("Segoe UI Light", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(240, 244, 248));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Path of File/Project:");

        chooseFileBtn.setBackground(new java.awt.Color(51, 78, 104));
        chooseFileBtn.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        chooseFileBtn.setForeground(new java.awt.Color(240, 244, 248));
        chooseFileBtn.setText("Choose");
        chooseFileBtn.setToolTipText("Browse for a file/folder in your computer");
        chooseFileBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        chooseFileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseFileBtnActionPerformed(evt);
            }
        });

        submitBtn.setBackground(new java.awt.Color(51, 78, 104));
        submitBtn.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        submitBtn.setForeground(new java.awt.Color(240, 244, 248));
        submitBtn.setText("Submit");
        submitBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        submitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                submitBtnActionPerformed(evt);
            }
        });

        output.setBackground(new java.awt.Color(34, 34, 34));
        output.setColumns(20);
        output.setFont(new java.awt.Font("Roboto Medium", 0, 24)); // NOI18N
        output.setForeground(new java.awt.Color(255, 255, 255));
        output.setRows(5);
        output.setFocusable(false);
        jScrollPane1.setViewportView(output);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(127, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(submitBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pathField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chooseFileBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(263, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chooseFileBtn, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pathField))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(submitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1038, 664));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void chooseFileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseFileBtnActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        fileChooser.showOpenDialog(new JFrame("Centered"));
        pathField.setText(fileChooser.getSelectedFile().getPath());
    }//GEN-LAST:event_chooseFileBtnActionPerformed

    private void submitBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_submitBtnActionPerformed

        File f = new File(pathField.getText());
        output.setText("");
        if(f.isDirectory()){
            File file = new File(f.getPath() + "\\src");
            String[] pathnames = file.list();
            for(String pathname : pathnames){
                File infile = new File(f.getPath() + "\\src" + "\\" + pathname);
                if(!infile.isDirectory()){
                    if(infile.getName().substring(infile.getName().lastIndexOf('.'), infile.getName().length()).equals(".java") || 
                       infile.getName().substring(infile.getName().lastIndexOf('.'), infile.getName().length()).equals(".cpp")){
                        scanFile(infile);
                    }
                }
            }
        } else {
            scanFile(f);
        }
    }//GEN-LAST:event_submitBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    
    public static void main(String args[]) throws FileNotFoundException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Details.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        
//        String[] pathnames;
//
//        // Creates a new File instance by converting the given pathname string
//        // into an abstract pathname
//        File fs = new File("E:\\CU\\prgrms\\JChess\\src\\Tile");
//        
//        System.out.println();

        // Populates the array with names of files and directories
//        pathnames = fs.list();
//
//        // For each pathname in the pathnames array
//        for (String pathname : pathnames) {
//            File newFile = new File("E:\\CU\\prgrms\\JChess\\src\\" + pathname);
//            System.out.println(newFile.getName().substring(newFile.getName().indexOf("."), newFile.getName().length()));
//        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Details().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chooseFileBtn;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JTextArea output;
    private javax.swing.JTextField pathField;
    private javax.swing.JButton submitBtn;
    // End of variables declaration//GEN-END:variables
}

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author pedroohhss
 */
public class UsuarioDAO {
    
    private Connection conexao;
        
    //Construtor
    public UsuarioDAO()throws Exception {
        try {
            //Conexão com o banco de dados PostgreSQL
            //Lembrar de alterar o nome do banco de dados que irá utilizar
            String url = "jdbc:postgresql://localhost:5432/loginRegister";
            //Se o nome de usuario for diferente do padrão, alterar também
            String usuario = "postgres";
            //Coloque a senha que você utiliza para acessar o pgadmin
            String senha = "1234";
            conexao = DriverManager.getConnection(url, usuario, senha);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            System.exit(1);
        }
    }//Fim construtor
    
    //Registrar no banco de dados
    public boolean registrar(String user, String pass) throws SQLException{
        try {
 
            PreparedStatement estado;

            String sqlInsert = "INSERT INTO usuario VALUES(?,?)";
            estado = conexao.prepareStatement(sqlInsert, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            estado.setString(1, user);
            estado.setString(2, pass);
            
            boolean teste = estado.execute();
            System.out.println(teste);
            if (teste = true) {
                return true;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            System.exit(1);
        }
        return false;
}
    
    //Consultar Banco de Dados
    public void consultar() throws Exception{
        final String sqlSelect = "SELECT * FROM usuario";
        try {
            PreparedStatement estado = conexao.prepareStatement(sqlSelect);
            
            ResultSet resultado = estado.executeQuery();
            
            System.out.println("ID  " + "USER     " + "PASSWORD");
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String user = resultado.getString("name");
                String pass = resultado.getString("password");
                System.out.println(id + "   " + user + "     " + pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    //Efetuar login
    public boolean login(String user, String pass) {
        try {
            
            String sqlSelect = "SELECT user,password FROM usuario WHERE name=? and password=?";
            PreparedStatement estado = conexao.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            estado.setString(1, user);
            estado.setString(2, pass);
            
            ResultSet resultado = estado.executeQuery();
            
            if (resultado.first()) {
                return true;
            }

        } catch (SQLException e) {
            System.err.println(e);
        }
        return false;
    }
}

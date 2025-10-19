
import ConexionDBA.ListasDBA;
import ModeloEntidad.Usuario.Usuario;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alejandro
 */
public class prueba {
    
    public static void main(String[] args) {
        
    System.out.println("🧪 INICIANDO PRUEBA DE LISTASDBA...");
        
        ListasDBA listasDBA = new ListasDBA();
        
        
        
        // Probar lista de usuarios
        System.out.println("\n👥 Probando lista de usuarios...");
        List<Usuario> usuarios = listasDBA.listaAdminCine();
        
        System.out.println("📊 Total de usuarios obtenidos: " + usuarios.size());
        
        for (Usuario usuario : usuarios) {
            System.out.println("➡️ Usuario: " + usuario.getNombre() + " - " + usuario.getRolUsuario());
        }
        
        System.out.println("✅ PRUEBA COMPLETADA");
    }
}

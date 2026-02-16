package Usuario;

import java.io.*;
import java.util.*;

public class PersistenciaDados {
    private static final String DIRETORIO_BASE = System.getProperty("user.dir") + "/dados";
    private static final String ARQUIVO_USUARIOS = "Usuarios.txt";
    
    public static void salvarDados(Usuario usuario) {
        File diretorio = new File(DIRETORIO_BASE);
        if (!diretorio.exists()) {
            diretorio.mkdir();
        }
        salvarRegistro(usuario);
        salvarUsuario(usuario);
    }

    private static void salvarRegistro(Usuario usuario) {
        String pathUsuarios = DIRETORIO_BASE + "/" + ARQUIVO_USUARIOS;
        List<String> linhas = new ArrayList<>();
        boolean usuarioExiste = false;

        File arquivoUsuarios = new File(pathUsuarios);
        if (arquivoUsuarios.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(";");
                    if (partes.length > 0 && partes[0].equals(usuario.verNomeUsuario())) {
                        usuarioExiste = true;
                        linhas.add(formatarRegistro(usuario));
                    } else {
                        linhas.add(linha);
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler o arquivo de usuários: " + e.getMessage());
            }
        }

        if (!usuarioExiste) {
            linhas.add(formatarRegistro(usuario));
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathUsuarios))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de usuários: " + e.getMessage());
        }
    }

    private static String formatarRegistro(Usuario usuario) {
        return usuario.verNomeUsuario() + ";" + usuario.verSenha();
    }

    private static void salvarUsuario(Usuario usuario) {
        String pathUsuario = DIRETORIO_BASE + "/" + usuario.verNomeUsuario() + ".dat";
        
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(pathUsuario))) {
            oos.writeObject(usuario);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o objeto do usuário '" + usuario.verNomeUsuario() + "': " + e.getMessage());
        }
    }

    public static Usuario carregarUsuario(String nomeUsuario) {
        if (!verificarUsuarioExistente(nomeUsuario)) {
            return null;
        }

        String pathUsuario = DIRETORIO_BASE + "/" + nomeUsuario + ".dat";
        File arquivoDados = new File(pathUsuario);

        if (arquivoDados.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivoDados))) {
                return (Usuario) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar o usuário '" + nomeUsuario + "': " + e.getMessage());
                return null;
            }
        } else {
            return new Usuario(nomeUsuario, "");
        }
    }

    public static boolean verificarUsuarioExistente(String nomeUsuario) {
        String pathUsuarios = DIRETORIO_BASE + "/" + ARQUIVO_USUARIOS;
        File arquivoUsuarios = new File(pathUsuarios);

        if (!arquivoUsuarios.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 1 && partes[0].equals(nomeUsuario)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao verificar existência de usuário: " + e.getMessage());
        }

        return false;
    }

    public static boolean verificarRegistro(String nomeUsuario, String senha) {
        String pathUsuarios = DIRETORIO_BASE + "/" + ARQUIVO_USUARIOS;
        File arquivoUsuarios = new File(pathUsuarios);

        if (!arquivoUsuarios.exists()) {
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length >= 2 && partes[0].equals(nomeUsuario) && partes[1].equals(senha)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao verificar registro de login: " + e.getMessage());
        }

        return false;
    }
    
    public static Usuario atualizarRegistro(String nomeAntigo, String novoNome, String senhaAntiga, String novaSenha) throws IOException {
        if (!nomeAntigo.equals(novoNome) && verificarUsuarioExistente(novoNome)) {
            return null;
        }

        if (!verificarRegistro(nomeAntigo, senhaAntiga)) {
            return null;
        }

        Usuario usuario = carregarUsuario(nomeAntigo);
		if (usuario == null) {
		    return null;
		}
		removerUsuario(nomeAntigo, senhaAntiga);
		usuario.mudarNomeUsuario(novoNome);
		usuario.mudarSenha(novaSenha);
		salvarDados(usuario);
		
		return usuario;
    }
    
    public static boolean removerUsuario(String nomeUsuario, String senha) {
        if (!verificarRegistro(nomeUsuario, senha)) {
            return false;
        }
        
        try {
            removerDoArquivoUsuarios(nomeUsuario);
            removerArquivoUsuario(nomeUsuario);
            
            return true;
        } catch (IOException e) {
            System.err.println("Erro ao remover usuário '" + nomeUsuario + "': " + e.getMessage());
            return false;
        }
    }

    private static void removerDoArquivoUsuarios(String nomeUsuario) throws IOException {
        String pathUsuarios = DIRETORIO_BASE + "/" + ARQUIVO_USUARIOS;
        File arquivoUsuarios = new File(pathUsuarios);
        List<String> linhas = new ArrayList<>();

        if (arquivoUsuarios.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    String[] partes = linha.split(";");
                    if (partes.length > 0 && !partes[0].equals(nomeUsuario)) {
                        linhas.add(linha);
                    }
                }
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathUsuarios))) {
            for (String linha : linhas) {
                bw.write(linha);
                bw.newLine();
            }
        }
    }

    private static void removerArquivoUsuario(String nomeUsuario) throws IOException {
        String pathUsuario = DIRETORIO_BASE + "/" + nomeUsuario + ".dat";
        File arquivoUsuario = new File(pathUsuario);
        
        if (arquivoUsuario.exists() && !arquivoUsuario.delete()) {
            throw new IOException("Falha ao remover arquivo do usuário");
        }
    }
}

import os
import firebase_admin
from firebase_admin import credentials, auth
from datetime import datetime

# Caminho para o JSON da chave de serviço
cred_path = os.path.join(os.path.dirname(__file__), "serviceAccountKey.json")
cred = credentials.Certificate(cred_path)
firebase_admin.initialize_app(cred) 


# Inicializa o cliente do Firestore
db = firestore.client()

def salvar_atividade(uid_usuario, nome_atividade):
    """Salva uma atividade concluída por um usuário."""
    
    dados_atividade = {
        'usuario_id': uid_usuario,
        'nome_atividade': nome_atividade,
        'data_conclusao': datetime.now()  # para gerar gráficos semanais
    }
    
    # Salvar na coleção "atividades"
    db.collection('atividades').add(dados_atividade)
    print("Atividade salva com sucesso!")

# Exemplo de uso
#if _name_ == "_main_":
#    salvar_atividade("usuario_123", "Exercício de Leitura")
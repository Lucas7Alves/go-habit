from flask import Blueprint, jsonify

grafico_bp = Blueprint('grafico', __name__)

# Rota para fornecer os dados do gráfico
@grafico_bp.route('/api/dados', methods=['GET'])
def get_dados():
    dados = [
        { "nome": "Meta 1", "valor": 80 },
        { "nome": "Meta 2", "valor": 60 },
        { "nome": "Meta 3", "valor": 90 },
        { "nome": "Meta 4", "valor": 70 }
    ]
    return jsonify(dados)

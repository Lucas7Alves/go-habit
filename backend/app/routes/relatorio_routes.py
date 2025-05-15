from flask import Blueprint, jsonify
from app.controllers.relatorio_controller import gerar_relatorio

relatorio_bp = Blueprint('relatorio', __name__)

@relatorio_bp.route('/relatorio', methods=['GET'])
def gerar():
    try:
        resultado = gerar_relatorio()
        return jsonify({"relatorio": resultado})
    except Exception as e:
        return jsonify({"erro": str(e)}), 500

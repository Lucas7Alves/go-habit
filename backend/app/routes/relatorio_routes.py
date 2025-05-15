from flask import Blueprint, jsonify
from app.controllers.relatorio_controller import gerar_relatorio

relatorio_bp = Blueprint('relatorio', __name__)

@relatorio_bp.route('/relatorio', methods=['GET'])
def relatorio():
    texto_relatorio = gerar_relatorio()
    return jsonify({"relatorio": texto_relatorio})

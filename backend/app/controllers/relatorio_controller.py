import google.generativeai as genai
import os
from dotenv import load_dotenv
import json

load_dotenv()

# Configura a API Key
genai.configure(api_key=os.getenv("API_KEY"))

def gerar_relatorio():
    with open("userDataMock.json", "r", encoding="utf-8") as f:
        user_data = json.load(f)

    dados_formatados = json.dumps(user_data, indent=2, ensure_ascii=False)

    prompt = f"""Compare a semana atual com a semana anterior, levando em consideração os dados das metas do usuário.

Sempre comece o relatório com a seguinte frase: "Olá {user_data.get('name')}, vamos dar uma olhada no seu relatório desta semana?"

dados do usuário:
{dados_formatados}
"""

    model = genai.GenerativeModel("gemini-1.5-flash")

    response = model.generate_content(prompt)

    return response.text

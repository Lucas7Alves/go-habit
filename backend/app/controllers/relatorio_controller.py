import google.generativeai as genai
from google.generativeai.types import GenerationConfig
import os
from dotenv import load_dotenv
import json

load_dotenv()

# Configura a API Key
genai.configure(api_key=os.getenv("GOOGLE_API_KEY"))

def gerar_relatorio():
    with open("userDataMock.json", "r", encoding="utf-8") as f:
        user_data = json.load(f)

    dados_formatados = json.dumps(user_data, indent=2, ensure_ascii=False)

    prompt = f"""
    Você é um assistente que escreve relatórios.

    NUNCA use os seguintes símbolos no texto do relatório: `*`, `@`, `#`, `%`, `!`.
    Use apenas texto simples. NÃO use asteriscos para listas, destaques ou ênfases.

    Compare a semana atual com a semana anterior com base nas metas do usuário.

    ao final do relatório, dê um feedback ao usuário sobre a realização das metas, aponte os pontos positivos e negativos das metas realizadas ou pendentes.


Sempre comece o relatório com a seguinte frase: "Olá {user_data.get('name')}, vamos dar uma olhada no seu relatório desta semana?"

dados do usuário:
{dados_formatados}
"""

    model = genai.GenerativeModel("gemini-1.5-flash")

    config = GenerationConfig(
        max_output_tokens=500,
        temperature=0.1,
    )

    response = model.generate_content(prompt, generation_config=config)

    return response.text

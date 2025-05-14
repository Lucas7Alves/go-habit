import { GoogleGenAI } from "@google/genai";
import fs from "node:fs/promises";

const ai = new GoogleGenAI({ apiKey: "" });

async function main() {

    try {

        const filePath = "./userDataMock.json";

        const fileContent = await fs.readFile(filePath, "utf-8");
        
        const userData = JSON.parse(fileContent);

        const prompt = `Compare o tempo de conclusão das metas da semana atual com as da semana anterior, com base nos seguintes dados do usuário: ${JSON.stringify(userData)}`;

        const response = await ai.models.generateContent({
    model: "gemini-2.0-flash",
    contents: prompt,

    config: {
        systemInstruction: "você é um instrutor de saúde e bons hábitos",
        maxOutputTokens: 500
    }
  });
  console.log(response.text);
    } catch (error) {
        console.error("Ocorreu um erro:", error);
    }
}

//main();
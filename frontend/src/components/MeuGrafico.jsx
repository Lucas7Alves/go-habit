import React, { useEffect, useState } from 'react';
import { BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid,ResponsiveContainer } from 'recharts';

export default function MeuGrafico() {
  const [dados, setDados] = useState([]);

  useEffect(() => {
    fetch('http://localhost:5000/api/dados')
      .then((res) => res.json())
      .then((data) => setDados(data))
      .catch((err) => console.error('Erro ao buscar dados:', err));
  }, []);

  return (
    <ResponsiveContainer width="40%" height={300}>
      <BarChart data={dados}>
        <CartesianGrid strokeDasharray="3 3" />
        <XAxis dataKey="nome" />
        <YAxis
          domain={[0, 100]}
          tickFormatter={(value) => `${value}%`} // eixo Y com %
        />
        <Tooltip
          formatter={(value) => `${value}%`} // tooltip com %
        />
        <Bar dataKey="valor" fill="#6D27AF" />
      </BarChart>
    </ResponsiveContainer>
  );
}
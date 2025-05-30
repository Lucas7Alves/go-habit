import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import Relatorio from '../components/Relatorio';

jest.mock('../components/UseMediaQuery.jsx', () => () => false);

const mockGoals = [
  { goal: 'Beber água', status: 'concluída' },
  { goal: 'Fazer exercício', status: 'pendente' },
  { goal: 'Estudar React', status: 'concluída' },
];

global.fetch = jest.fn(() =>
  Promise.resolve({
    json: () => Promise.resolve({ relatorio: 'Análise gerada pela IA.' }),
  })
);

describe('Relatorio', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  test('renderiza relatório de metas com dados fornecidos', async () => {
    render(<Relatorio goals={mockGoals} />);

    expect(screen.getByText(/Relatório de Metas/i)).toBeInTheDocument();
    expect(screen.getByText(/Você tem um total de 3 metas/i)).toBeInTheDocument();
    expect(screen.getByText(/Beber água/i)).toBeInTheDocument();
    expect(screen.getByText(/Fazer exercício/i)).toBeInTheDocument();
    expect(screen.getByText(/Estudar React/i)).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText(/Análise da Semana/i)).toBeInTheDocument();
      expect(screen.getByText(/Análise gerada pela IA/i)).toBeInTheDocument();
    });
  });

  test('exibe erro se fetch falhar', async () => {
    fetch.mockImplementationOnce(() => Promise.reject(new Error('Erro de rede')));

    render(<Relatorio goals={mockGoals} />);

    await waitFor(() => {
      expect(screen.getByText(/Erro ao carregar análise da IA/i)).toBeInTheDocument();
    });
  });
});

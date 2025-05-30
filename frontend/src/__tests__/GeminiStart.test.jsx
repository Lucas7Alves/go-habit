import React from 'react';
import { render, screen, waitFor } from '@testing-library/react';
import RelatorioSemanal from '../components/geminiStart';

global.fetch = jest.fn(() =>
  Promise.resolve({
    json: () => Promise.resolve({ relatorio: 'Relatório de teste da IA' }),
  })
);

describe('RelatorioSemanal', () => {
  beforeEach(() => {
    fetch.mockClear();
  });

  test('renderiza título e relatório quando resposta for válida', async () => {
    render(<RelatorioSemanal />);

    expect(screen.getByText('Relatório Semanal')).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText('Relatório de teste da IA')).toBeInTheDocument();
    });
  });

  test('exibe mensagem de erro quando fetch falhar', async () => {
    fetch.mockImplementationOnce(() => Promise.reject(new Error('Erro de rede')));

    render(<RelatorioSemanal />);

    await waitFor(() => {
      expect(screen.getByText(/erro de rede/i)).toBeInTheDocument();
    });
  });
});

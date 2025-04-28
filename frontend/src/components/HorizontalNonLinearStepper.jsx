import * as React from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepButton from '@mui/material/StepButton';
import Button from '@mui/material/Button';

const steps = ['Cadastrando a conta', 'Criando perfil'];

export default function HorizontalNonLinearStepper({ step, handleNextStep, handleBackStep }) {
  return (
    <Box sx={{ width: '100%' }}>
      <Stepper nonLinear activeStep={step} sx={{ maxWidth: '33vw', margin: '0 auto' }}>
        {steps.map((label, index) => {
          const isActive = index === step;

          return (
            <Step key={label}>
              <StepButton
                color="inherit"
                sx={{
                  height: '0px',
                  flexDirection: 'column',
                  '& .MuiStepLabel-label': {
                    color: isActive ? '#996AF9' : 'rgba(197, 150, 241, 0.5)',
                  },
                  '& .MuiStepIcon-root': {
                    color: isActive ? '#996AF9' : 'rgba(197, 150, 241, 0.4)',
                  },
                  '& .MuiStepIcon-text': {
                    fill: 'white',
                  },
                }}
              >
                {label}
              </StepButton>
            </Step>
          );
        })}
      </Stepper>

      <Box sx={{ display: 'flex', flexDirection: 'row', pt: 2 }}>
        <Button
          onClick={handleBackStep}
          disabled={step === 0}
          sx={{
            mr: 1,
            marginBottom: '30px',
            color: step === 0 ? 'rgba(153, 106, 249, 0.5)' : '#996AF9',
            '&:disabled': {
              color: 'rgba(197, 150, 241, 0.5)',
            },
            '&:hover': {
              backgroundColor: 'rgba(197, 150, 241, 0.2)',
            },
            '&:focus': {
              outline: 'none',
            },
          }}
        >
          Voltar
        </Button>
        <Box sx={{ flex: '1 1 auto' }} />
          <Button
            onClick={handleNextStep}
            disabled={step === 1}
            sx={{
              mr: 1,
              marginBottom: '30px',
              color: step === 1 ? 'rgba(153, 106, 249, 0.5)' : '#996AF9',
              '&:disabled': {
                color: 'rgba(197, 150, 241, 0.5)',
              },
              '&:hover': {
                backgroundColor: 'rgba(197, 150, 241, 0.2)',
              },
              '&:focus': {
                outline: 'none',
              },
            }}
          >
            Próximo
          </Button>
        
      </Box>
    </Box>
  );
}
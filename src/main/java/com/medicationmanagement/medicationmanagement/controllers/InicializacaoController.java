package com.medicationmanagement.medicationmanagement.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicationmanagement.medicationmanagement.entities.Estoque;
import com.medicationmanagement.medicationmanagement.entities.Farmacia;
import com.medicationmanagement.medicationmanagement.entities.Medicamento;
import com.medicationmanagement.medicationmanagement.entities.Medicamento.TipoMedicamento;
import com.medicationmanagement.medicationmanagement.repositories.EstoqueRepository;
import com.medicationmanagement.medicationmanagement.repositories.FarmaciaRepository;
import com.medicationmanagement.medicationmanagement.repositories.MedicamentoRepository;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/inicializacao")
public class InicializacaoController {

    private final FarmaciaRepository farmaciaRepository;
    private final MedicamentoRepository medicamentoRepository;
    private final EstoqueRepository estoqueRepository;

    public InicializacaoController(FarmaciaRepository farmaciaRepository, MedicamentoRepository medicamentoRepository, EstoqueRepository estoqueRepository) {
        this.farmaciaRepository = farmaciaRepository;
        this.medicamentoRepository = medicamentoRepository;
        this.estoqueRepository = estoqueRepository;
    }

    @PostMapping
    public void popularDados() {
        carregarFarmacias();
        carregarMedicamentos();
        carregarEstoques();
    }

    private void carregarFarmacias() {
        Farmacia[] farmacias = {
            new Farmacia(90561736000121L, "DevMed Ltda", "Farmácia DevMed", "devmed@farmacia.com", "(44)4444-4444", "(44)9444-4441", 88888999L, "Rua Porto Real", 67, "Westeros", "Berlim", "SC",  15.23456, 2.8678687),
            new Farmacia(43178995000198L, "MedHouse Ltda", "Farmácia MedHouse", "medhouse@farmacia.com", "(55)5555-5555", "(45)95555-5555", 8877799L, "Rua Madrid", 76, "Winterfell", "Estocolmo", "SC", 19.254356, 3.8987687)
        };

        for (Farmacia farmacia : farmacias) {
            farmaciaRepository.save(farmacia);
        }
    }

    private void carregarMedicamentos() {
        Medicamento[] medicamentos = {
            new Medicamento(1010, "Programapan", "Matrix", "2x ao dia", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc eleifend", 111.00f, TipoMedicamento.COMUM),
            new Medicamento(7473, "Cafex", "Colombia Farm", "4x ao dia", "Pellentesque non ultricies mauris, ut lobortis elit. Cras nec cursus nibh", 51.50f, TipoMedicamento.COMUM),
            new Medicamento(2233, "Estomazol", "Acme", "1x ao dia", "Sed risus mauris, consectetur eget egestas vitae", 22.50f, TipoMedicamento.COMUM),
            new Medicamento(8880, "Gelox", "Ice", "2x ao dia", "Quisque quam orci, vulputate sit amet", 11.50f, TipoMedicamento.COMUM),
            new Medicamento(5656, "Aspirazol", "Acme", "3x ao dia", "Sed faucibus, libero iaculis pulvinar consequat, augue elit eleifend", 10.50f, TipoMedicamento.CONTROLADO),
            new Medicamento(4040, "Propolizol", "Bee", "5x ao dia", "Nunc euismod ipsum purus, sit amet finibus libero ultricies in", 10.50f, TipoMedicamento.CONTROLADO)
            // Adicione outros objetos Medicamento conforme necessário
        };

        for (Medicamento medicamento : medicamentos) {
            medicamentoRepository.save(medicamento);
        }
    }

    private void carregarEstoques() {
        Estoque[] estoques = {
            new Estoque(90561736000121L, 1010, 12, LocalDateTime.now()),
            new Estoque(90561736000121L, 7473, 10, LocalDateTime.now()),
            new Estoque(43178995000198L, 7473, 2, LocalDateTime.now()),
            new Estoque(43178995000198L, 2233, 15, LocalDateTime.now()),
            new Estoque(43178995000198L, 8880, 16, LocalDateTime.now()),
            new Estoque(43178995000198L, 4040, 22, LocalDateTime.now())
            // Adicione outros objetos Estoque conforme necessário
        };

        for (Estoque estoque : estoques) {
            estoqueRepository.save(estoque);
        }
    }
}

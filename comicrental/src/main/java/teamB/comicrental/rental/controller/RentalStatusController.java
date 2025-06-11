package teamB.comicrental.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import teamB.comicrental.rental.model.Rental;
import teamB.comicrental.rental.repository.RentalMapper;

import java.util.List;

@Controller
public class RentalStatusController {

    @Autowired
    private RentalMapper rentalMapper;

    @GetMapping("/rental/status")
    public String showRentalStatus(Model model) {
        List<Rental> currentRentals = rentalMapper.findCurrentRentals();
        model.addAttribute("currentRentals", currentRentals);
        return "rental/status";
    }
}

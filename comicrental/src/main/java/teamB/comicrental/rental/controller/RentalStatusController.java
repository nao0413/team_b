package teamB.comicrental.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import teamB.comicrental.rental.RentalService;
import teamB.comicrental.rental.model.Rental;
import teamB.comicrental.rental.repository.RentalMapper;
import java.util.List;

@Controller
public class RentalStatusController {

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rental/status")
    public String showRentalStatus(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer customerId = (Integer) session.getAttribute("loggedInUserId");
        if (customerId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/login/loginpage";
        }
        List<Rental> currentRentals = rentalMapper.findCurrentRentals(customerId);
        model.addAttribute("currentRentals", currentRentals);
        return "rental/status";
    }

    @PostMapping("/rental/confirm")
    public String confirmRental(HttpSession session) {
        Integer customer_id = (Integer) session.getAttribute("loggedInUserId");
        rentalService.confirmRental(customer_id);
        return "redirect:/cart/complete";
    }

    @GetMapping("/rental/confirm")
    public String redirectToCart() {
        return "redirect:/cart/cart_confirm";
    }
    
}
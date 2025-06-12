package teamB.comicrental.comictable.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ComicService {
    @Autowired
    private ComicMapper comicMapper;

    public List<ComicModel> getComicsBasedOnSortOrder(String sortOrder,Integer customerId){
        switch(sortOrder){
            case "arrivalDate":
            return comicMapper.findAllComicsSortedByArrivalDate(customerId);
            case "rentalCount":
            return comicMapper.findAllComicsSortedByRentaltimes(customerId);
            case "comicId":
            default:
            return comicMapper.findAllComicsWithCategoryAndRentalStatus(customerId);

        }
    }
}

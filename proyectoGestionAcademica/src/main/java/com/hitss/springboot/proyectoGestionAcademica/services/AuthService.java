import com.hitss.springboot.proyectoGestionAcademica.dto.AuthResponseDto;
import com.hitss.springboot.proyectoGestionAcademica.dto.LoginRequestDto;
import com.hitss.springboot.proyectoGestionAcademica.dto.RegisterRequestDto;
import com.hitss.springboot.proyectoGestionAcademica.entities.User;

public interface AuthService  {
    AuthResponseDto register(RegisterRequestDto request);
    AuthResponseDto login(LoginRequestDto request);
    User getCurrentUser();
}

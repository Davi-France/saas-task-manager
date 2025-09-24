import { Component } from '@angular/core';
import { AuthService } from '../../services/authService';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterModule, HttpClientModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
  standalone: true
})
export class Login {
  email = ""
  password = ""
  isRegister = false

  constructor(private authService: AuthService, private router: Router) { }

  isRegisterUser() {
    this.isRegister = !this.isRegister
  }

  submit() {
    if (this.isRegister) {
      this.authService.register(this.email, this.password).subscribe(() => {
        alert("usuario novo cadastrado")
        this.isRegister = false
      })
    } else {
      this.authService.login(this.email, this.password).subscribe((res: any) => {
        this.authService.saveToken(res.token)
        this.router.navigate(['/home'])
      })
    }

  }
}

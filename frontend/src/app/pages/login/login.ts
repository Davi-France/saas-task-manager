import { Component } from '@angular/core';
import { AuthService } from '../../services/authService';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule, RouterModule, HttpClientModule, MatIconModule],
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

    this.authService.login(this.email, this.password).subscribe((res: any) => {
      this.authService.saveToken(res.token)
      this.router.navigate(['/home'])
    })


  }

  submitRegister() {
    this.authService.register(this.email, this.password).subscribe(() => {
      alert("usuario novo cadastrado")
      this.isRegister = false
    })
    this.closeRegister();
  }

  openRegister() {
    this.isRegister = true;
  }

  closeRegister() {
    this.isRegister = false;
  }
}

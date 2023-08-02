import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from "@angular/forms";


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent {

  longUrlFC = new FormGroup({longUrl: new FormControl("", [
    Validators.required,
    Validators.minLength(10),
    Validators.maxLength(10000),
    Validators.pattern("^(https?|ftp)://[^\s/$.?#].[^\s]*$")
])});
}

import { Component, EventEmitter, Output } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, Validators } from "@angular/forms";
import { UrlModel } from 'src/app/model/url';
//import { UrlModel } from 'src/app/model/url';
import { URLServiceService } from 'src/app/services/urlservice.service';


@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.scss']
})
export class FormComponent {

  @Output() newShortUrlEvent = new EventEmitter<string>();
  @Output() newLongUrlEvent = new EventEmitter<string>();
  shortUrl: string = "";
  longUrl: string = "";
  urlModel: UrlModel = {
    longUrl: "",
    //shortUrl: ""
  };

  constructor(private urlService: URLServiceService) { }

  longUrlFC = new FormGroup({longUrl: new FormControl("", [
    Validators.required,
    Validators.minLength(10),
    Validators.maxLength(1000),
    Validators.pattern("^(https?|ftp)://[^\s/$.?#].[^\s]*$")
])});

  get url() {
    return this.longUrlFC.get('longUrl');
  }

  invalidUrl:boolean = false;

  onSubmit(){
    if(this.url?.invalid) {
      this.invalidUrl = true;
    }  else {
      if(this.url?.value) {
        this.urlModel.longUrl = this.url.value;
      }
      this.urlService.getShortUrlFromLongUrl(this.urlModel).subscribe({
        next: (resp) => {
          console.log(resp);
          this.shortUrl = resp.shortUrl;
          console.log(this.urlModel);
          this.newShortUrlEvent.emit(this.shortUrl);
          this.newLongUrlEvent.emit(this.urlModel.longUrl);
          
        },
        error: (err: any) => {
          console.log(err);
        },
      })

      
    }
  }
}

import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  getAuthorizationToken(){
    return "ABC";
  }

  validateToken(token:string):boolean{
    return true;
  }

  
}

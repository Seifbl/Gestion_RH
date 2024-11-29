import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

import {TokenStorageService} from "../../../../core/guard/token-storage.service";

@Component({
  selector: 'app-updateuser',
  templateUrl: './updateuser.component.html',
  styleUrls: ['./updateuser.component.scss']
})
export class UpdateuserComponent implements OnInit {




  
  addCustomUser(term: any) {

    console.log(term);
  

    //console.log(term);
  }
  ngOnInit(): void {
   
    }


  }
 


  



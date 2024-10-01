import { Component, OnInit } from '@angular/core';
import { CarService } from '../../service/car.service';
import { Car } from '../../model/car';
import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute  } from '@angular/router';
@Component({
  selector: 'app-view',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './view.component.html',
  styleUrl: './view.component.css'
})
export class ViewComponent implements OnInit  {

  car: any
  id: string | null = '';


  constructor(private route: ActivatedRoute, public carService: CarService) { }

  ngOnInit(): void {
    // Get the car ID from the route
    const id: string = this.route.snapshot.paramMap.get('carId') || '';

    // Fetch car details using the ID
    this.carService.getCarById(id).subscribe({
      next: (data) => { this.car = data },
      error: (error) => { console.error(error); }
    });
  }

}

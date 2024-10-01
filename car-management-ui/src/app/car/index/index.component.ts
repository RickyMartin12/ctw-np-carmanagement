import { Component } from '@angular/core';

import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { CarService } from '../../service/car.service';
import { Car } from '../../model/car';

@Component({
  selector: 'app-index',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './index.component.html',
  styleUrl: './index.component.css'
})
export class IndexComponent {

  cars: Car[] = []

  constructor(public carService: CarService) { }
      
  /**
   * Write code on Method
   *
   * @return response()
   */
  ngOnInit(): void {
    this.loadCars(); 
  }

  loadCars(): void {
    this.carService.getAll().subscribe((data: Car[])=>{
      this.cars = data;
    });
  }

  deleteCar(carId: string | undefined): void {
    if (confirm('Are you sure you want to delete this car with id '+carId+'?')) { // Confirm before deletion
      this.carService.deleteCar(carId).subscribe({
        next: (response) => {
          console.log('Car deleted successfully!', response);
          this.loadCars(); // Reload the cars after deletion to update the list
        },
        error: (error) => {
          console.error('There was an error deleting the car!', error);
        }
      });
    }
  }

  
}

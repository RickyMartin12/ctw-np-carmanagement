import { Component } from '@angular/core';
import { Car } from '../../model/car';
import { CarService } from '../../service/car.service';
import { CommonModule } from '@angular/common';
import { RouterModule  } from '@angular/router';
import { FormsModule,NgForm  } from '@angular/forms'; // Import FormsModule

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './create.component.html',
  styleUrl: './create.component.css'
})
export class CreateComponent {
  brand: string = '';  // Ensure this is a string, not an object
  model: string = '';
  engineType: string = '';

  constructor(private carService: CarService) { }

  onSubmit(carForm: NgForm) {
    if (carForm.valid) {
        const model = carForm.controls['model'].value;  // Using bracket notation
        const brand = carForm.controls['brand'].value;  // Using bracket notation
        const engineType = carForm.controls['engineType'].value;  // Using bracket notation

        // Now you can call the createCar method
        this.carService.createCar(model, brand, engineType).subscribe({
            next: (response) => {
                console.log('Car created successfully!', response);
            },
            error: (error) => {
                console.error('There was an error!', error);
            }
        });
    }
  }

}

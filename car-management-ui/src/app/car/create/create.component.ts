import { Component } from '@angular/core';
import { Router } from '@angular/router'; // Import Router
import { CarService } from '../../service/car.service';
import { CommonModule } from '@angular/common';
import { RouterModule  } from '@angular/router';
import { FormsModule,NgForm  } from '@angular/forms'; // Import FormsModule

@Component({
  selector: 'app-create',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'] // Fix typo: styleUrl -> styleUrls
})
export class CreateComponent {
  brand: string = '';  // Ensure this is a string, not an object
  model: string = '';
  engineType: string = '';
  selectedFile: File | null = null;  // Variable to store the selected file
  color: string = '';

  // To hold validation messages
  validationMessages: string[] = [];
  successMessage: string = ''; // Initialize the success message

  constructor(private carService: CarService, private router: Router) { }

  // Handle file input selection
  onFileSelected(event: any): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      const file = input.files[0]; // Get the selected file
      const modifiedFileName = this.normalizeFileName(file.name);
      console.log(modifiedFileName);
      this.selectedFile = new File([file], modifiedFileName, { type: file.type });
    }
  }

  normalizeFileName(fileName: string): string {
    return fileName
        .replace(/[^a-zA-Z0-9-_\. ]+/g, '')  // Remove special characters, keeping alphanumeric, hyphens, underscores, periods, and spaces
        .replace(/\s+/g, '-')                 // Replace spaces with hyphens
        .toLowerCase();                       // Convert to lower case
}

  onSubmit(carForm: NgForm) {
    this.validationMessages = []; // Clear previous messages

    if (!carForm.valid) {
      if (!this.brand) {
        this.validationMessages.push('Brand is required.');
      }
      if (!this.model) {
        this.validationMessages.push('Model is required.');
      }
      if (!this.engineType) {
        this.validationMessages.push('Engine Type is required.');
      }
      if(!this.color) {
        this.validationMessages.push('Color Car is required.');
      }
      return; // Stop form submission if invalid
    }
    else {
      //const model = carForm.controls['model'].value;  // Using bracket notation
        //const brand = carForm.controls['brand'].value;  // Using bracket notation
        //const engineType = carForm.controls['engineType'].value;  // Using bracket notation

        const formData = new FormData();
        console.log(this.selectedFile);
      formData.append('brand', this.brand);
      formData.append('model', this.model);
      formData.append('engineType', this.engineType);
      formData.append('color', this.color);
      if (this.selectedFile) {
        formData.append('image', this.selectedFile, this.selectedFile.name);  // Add image file to FormData
        formData.append('fileName', this.selectedFile.name)
      } 
  

      


        // Now you can call the createCar method
        this.carService.createCar2(formData).subscribe({
            next: (response) => {
              this.successMessage = 'Car '+response.brand+' and Model '+response.model+' created successfully!';
                //console.log('Car created successfully!', response);
                setTimeout(() => {
                  this.router.navigate(['/']); // Redirect to home page on success
                }, 5000); // 5000 milliseconds = 5 seconds
            },
            error: (error) => {
                console.error('There was an error!', error);
            }
        });
    }
  }

  

}

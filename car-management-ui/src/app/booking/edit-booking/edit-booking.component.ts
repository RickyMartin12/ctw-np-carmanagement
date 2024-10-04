import { Component, OnInit } from '@angular/core';
import { CarService } from '../../service/car.service';

import { CommonModule } from '@angular/common';
import { RouterModule, ActivatedRoute, Router } from '@angular/router';
import { FormsModule, NgForm } from '@angular/forms'; // Import FormsModule
import { MenuComponent } from '../../menu/menu.component'; // Import the MenuComponent

@Component({
  selector: 'app-edit-booking',
  standalone: true,
  imports: [CommonModule, RouterModule, FormsModule, MenuComponent],
  templateUrl: './edit-booking.component.html',
  styleUrls: ['./edit-booking.component.css' ]
})
export class EditBookingComponent implements OnInit {

  reservation: any = { 
    id: '', 
    name: '', 
    location: '', 
    contact_number: '', 
    license_number: '', 
    date_hour: '', 
    date_hour_fim: '', 
    car_id: '' 
};
  id: string | null = '';
  listCars: any[] = [];
  validationMessages: string[] = [];
  successMessage: string = ''; // For displaying success messages
  minDateTime: string = '';
  minEndDateTime: string = '';
  reservations: any[] = []; // To store the list of reservations

  constructor(private route: ActivatedRoute, public carService: CarService, private router: Router) { }

  



  ngOnInit(): void {
    // Get the car ID from the route
    const id: string = this.route.snapshot.paramMap.get('rersId') || '';
    let num: number = Number(id);
    
    // Fetch reservation details using the ID
    this.carService.getReservationById(num).subscribe({
      next: (data) => {

        
        const formatteddateHourInit = this.formatDate(data.dateHour);
        const formatteddateHourFim = this.formatDate(data.dateHourFim);
        this.reservation = {
          id: data.id.toString(),  // Ensure id is a string
          name: data.name,
          location: data.location,
          contact_number: data.contactNumber,
          license_number: data.licenseNumber,
          date_hour: formatteddateHourInit,  // Ensure the format is correct
          date_hour_fim: formatteddateHourFim,  // Ensure the format is correct
          car_id: data.car_id
        };
      },
      error: (error) => { console.error(error); }
    });

    const now = new Date();
    this.minDateTime = now.toISOString().slice(0, 16); 
    this.minEndDateTime = now.toISOString().slice(0, 16); 

    this.carService.getAllCarsSelected().subscribe({
      next: (data) => { 
        this.listCars = data; 
        console.log(data); 
      },
      error: (error) => { console.error(error); }
    });

    this.carService.getAllReservations().subscribe({
      next: (data) => {
        this.reservations = data; // Store the response
        //console.log(this.carByIds); // Log the array of car IDs
      },
      error: (error) => {
        console.error('Error fetching reservations:', error); // Handle error
      }
    }) 
  }

  formatDate(dateString: any) {
    const id: string = this.route.snapshot.paramMap.get('rersId') || '';
    let num: number = Number(id);
    // Create a Date object from the input string
    const date = new Date(dateString);
    
    // Get the individual components of the date
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const seconds = String(date.getSeconds()).padStart(2, '0');

    // Format the date and time as "YYYY-MM-DD HH:MM:SS"
    return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}

onStartDateChange(): void {
  if (this.reservation.date_hour) {
    const startDate = new Date(this.reservation.date_hour);
    this.minEndDateTime = startDate.toISOString().slice(0, 16); // Set the min end date to the start date
  } else {
    this.minEndDateTime = this.minDateTime; // Fallback to the default min date if no start date is selected
  }
}

validateDate(date: any) {
  const day = date.getDay(); // Get day of the week (0 = Sunday, 1 = Monday, ..., 6 = Saturday)
  const hours = date.getHours(); // Get hours of the day
  
  // Check if the day is Friday (5) after 20:00 or before Monday (1) 08:00
  if ((day === 5 && hours >= 20) || (day === 6) || (day === 0) || (day === 1 && hours <= 8)) {
    return true;
  }
  return false;
}

  onSubmit(reservationForm: NgForm): void {
    console.log(this.reservation.date_hour);

    let flag = 0;

    this.validationMessages = []; // Clear previous messages

    const selectedDate = new Date(this.reservation.date_hour);
    const selectedDateFim = new Date(this.reservation.date_hour_fim);

    const id: string = this.route.snapshot.paramMap.get('rersId') || '';
            let num: number = Number(id);

    console.log(this.reservation.name);

    if (!reservationForm.valid) {
      if (!this.reservation.name) {
        this.validationMessages.push('Name Person is required.');
      }
      if (!this.reservation.location) {
        this.validationMessages.push('Location is required.');
      }
      if (!this.reservation.contact_number) {
        this.validationMessages.push('Contact Number is required.');
      }
      if (!this.reservation.license_number) {
        this.validationMessages.push('License Number is required.');
      }
      if (!this.reservation.car_id) {
        this.validationMessages.push('CAR ID is required.');
      }
      if (!this.reservation.date_hour) {
        this.validationMessages.push('Start Data Hour is required.');
      }
      if (!this.reservation.date_hour_fim) {
        this.validationMessages.push('End Data Hour is required.');
      }
      
      return; // Stop form submission if invalid
    }
    else {
      if (selectedDateFim <= selectedDate) {
        // Show error if end date is earlier or equal to start date
        this.validationMessages.push('End date and time must be after the start date and time.');
      }
      else {

        const id: string = this.route.snapshot.paramMap.get('rersId') || '';

        if (this.validateDate(selectedDate) && this.validateDate(selectedDateFim)) {

          for (let i = 0; i < this.reservations.length; i++) {
            // Convert dateHour and dateHourFim from string to Date objects
            var data_hora_inicio_bd = new Date(this.reservations[i].dateHour);
            var data_hora_fim_bd = new Date(this.reservations[i].dateHourFim);
            console.log(id, this.reservations[i].id)
            if(id == this.reservations[i].id)
            {
              flag = 0;
              break;
            }
            else {
              if(this.reservation.car_id == this.reservations[i].car_id) {
                if((selectedDate <= data_hora_inicio_bd && selectedDateFim <= data_hora_inicio_bd) || (data_hora_fim_bd <= selectedDate && data_hora_fim_bd <= selectedDateFim))
                  {
                    flag = 0;
                  }
                  else {
                    flag = 1;
                    break;
                  }    
              }
            }
            

            
        
            
          }

          console.log(flag);

          if (flag == 1) {
            this.validationMessages.push("The Car cannot be booked between the dates. Choose another date our another car to booked");
          }
            const reservationData = {
              name: this.reservation.name,
              location: this.reservation.location,
              contactNumber: this.reservation.contact_number,
              licenseNumber: this.reservation.license_number,
              dataInicio: this.reservation.date_hour,
              dataFim: this.reservation.date_hour_fim,
              carId: this.reservation.car_id
            };

            //console.log(reservationData);
            console.log(num);
            this.carService.updateReservation(num, reservationData).subscribe({
              next: (response) => {
                console.log('Reservation edited successfully:', response);
                this.successMessage = 'Reservation '+response.name+' with car ID '+response.carId+' update successfully!';

                setTimeout(() => {
                  this.router.navigate(['/booking']); // Redirect to home page on success
                }, 5000); 
                //this.router.navigate(['/booking']); // Redirect to home page on success
              },
              error: (error) => {
                  console.error('There was an error!', error);
              }
          });

            
          

          
        } else {
          this.validationMessages.push("The Dates and times must be between Friday 20:00 and Monday 08:00.");
        }
      }
    }
    /*if (reservationForm.valid) {
      this.carService.createReservation(this.reservation).subscribe({
        next: () => {
          this.successMessage = 'Reservation created successfully!';
          this.validationMessages = []; // Clear validation messages
          // Optionally navigate to another page or reset the form
        },
        error: (error) => {
          this.validationMessages.push('Failed to create reservation.');
          console.error(error);
        }
      });
    } else {
      this.validationMessages.push('Please fill all required fields.');
    }*/
  }
}

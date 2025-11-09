import { Pipe, PipeTransform } from "@angular/core";

@Pipe({ name: 'titlecase'})
export class TitlecasePipe implements PipeTransform {
  transform(value: string): string {
    return value.toLowerCase().replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
  }
}
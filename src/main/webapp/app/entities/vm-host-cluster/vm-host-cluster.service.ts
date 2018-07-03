import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IVMHostCluster } from 'app/shared/model/vm-host-cluster.model';

type EntityResponseType = HttpResponse<IVMHostCluster>;
type EntityArrayResponseType = HttpResponse<IVMHostCluster[]>;

@Injectable({ providedIn: 'root' })
export class VMHostClusterService {
    private resourceUrl = SERVER_API_URL + 'api/vm-host-clusters';

    constructor(private http: HttpClient) {}

    create(vMHostCluster: IVMHostCluster): Observable<EntityResponseType> {
        return this.http.post<IVMHostCluster>(this.resourceUrl, vMHostCluster, { observe: 'response' });
    }

    update(vMHostCluster: IVMHostCluster): Observable<EntityResponseType> {
        return this.http.put<IVMHostCluster>(this.resourceUrl, vMHostCluster, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IVMHostCluster>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IVMHostCluster[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
